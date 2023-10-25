/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.account;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jmpsl.communication.mail.JmpslMailService;
import org.jmpsl.communication.mail.MailRequestDto;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.security.jwt.JwtService;
import org.jmpsl.security.user.AuthUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.miloszgilga.algorithms.GameAlgorithms;
import pl.miloszgilga.algorithms.LevelBoostRangeDto;
import pl.miloszgilga.domain.blacklist_jwt.BlacklistJwtEntity;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.exception.AuthException;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.network.account.reqdto.*;
import pl.miloszgilga.network.account.resdto.AccountDetailsResDto;
import pl.miloszgilga.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.network.account.resdto.UpdatedNameResDto;
import pl.miloszgilga.security.JwtIssuer;
import pl.miloszgilga.security.SecurityUtils;
import pl.miloszgilga.utils.MailTemplate;
import pl.miloszgilga.utils.SmtpUtils;
import pl.miloszgilga.utils.Utilities;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static pl.miloszgilga.exception.AccountException.*;
import static pl.miloszgilga.exception.AuthException.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
class AccountServiceImpl implements AccountService {
    private final SmtpUtils smtpUtils;
    private final JwtIssuer jwtIssuer;
    private final JwtService jwtService;
    private final SecurityUtils securityUtils;
    private final GameAlgorithms gameAlgorithms;
    private final PasswordEncoder passwordEncoder;
    private final JmpslMailService jmpslMailService;
    private final LocaleMessageService messageService;

    private final UserRepository userRepository;

    @Override
    public AccountDetailsResDto details(AuthUser authUser) {
        final UserEntity user = userRepository.findUserByLoginOrEmail(authUser.getUsername())
            .orElseThrow(UserNotFoundException::new);
        final LevelBoostRangeDto levelBoostRange = gameAlgorithms.getLevelBoostRange(user.getLevel());
        return AccountDetailsResDto.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .login(user.getLogin())
            .emailAddress(user.getEmailAddress())
            .role(user.getRole().getRole())
            .level(user.getLevel())
            .fromLevel(levelBoostRange.fromLevel())
            .toLevel(levelBoostRange.toLevel())
            .money(user.getMoney())
            .exp(user.getExp())
            .accountCreated(user.getCreatedAt())
            .build();
    }

    @Override
    public UpdatedNameResDto updateName(UpdateNameReqDto reqDto, AuthUser authUser) {
        final UserEntity user = securityUtils.getLoggedUser(authUser);
        user.setFirstName(StringUtils.capitalize(reqDto.getFirstName()));
        user.setLastName(StringUtils.capitalize(reqDto.getLastName()));
        userRepository.save(user);

        log.info("Successfully updated first and last name from '{}' to '{}'. User: {}", user, reqDto,
            user.getLogin());

        return UpdatedNameResDto.builder()
            .message(messageService.getMessage(AppLocaleSet.NEW_NAME_SET_RES,
                Map.of("login", user.getLogin())))
            .newFirstName(StringUtils.capitalize(reqDto.getFirstName()))
            .newLastName(StringUtils.capitalize(reqDto.getLastName()))
            .build();
    }

    @Override
    public UpdatedLoginResDto updateLogin(HttpServletRequest req, UpdateLoginReqDto reqDto, AuthUser authUser) {
        final String extractedToken = jwtService.extractToken(req);
        final UserEntity user = securityUtils.getLoggedUser(authUser);
        if (userRepository.checkIfUserWithSameLoginExist(reqDto.getNewLogin(), user.getId())) {
            throw new LoginAlreadyExistException(reqDto.getNewLogin());
        }
        final Claims claims = jwtService.extractClaims(extractedToken)
            .orElseThrow(() -> new AuthException.IncorrectJwtException(extractedToken));

        final BlacklistJwtEntity blacklistJwt = BlacklistJwtEntity.builder()
            .jwtToken(extractedToken)
            .expiredAt(ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZonedDateTime.now().getZone()))
            .build();

        user.setLogin(reqDto.getNewLogin());
        user.persistBlacklistJwt(blacklistJwt);
        final UserEntity savedUser = userRepository.save(user);

        log.info("Successfully updated login from '{}' to '{}'. User: {}", user.getLogin(), reqDto.getNewLogin(),
            user.getLogin());

        return UpdatedLoginResDto.builder()
            .message(messageService.getMessage(AppLocaleSet.NEW_EMAIL_ADDRESS_SET_RES,
                Map.of("login", user.getLogin())))
            .newLogin(reqDto.getNewLogin())
            .updatedJwt(jwtIssuer.generateTokenForUser(savedUser))
            .build();
    }

    @Override
    public UpdatedEmailResDto updateEmail(UpdateEmailReqDto reqDto, AuthUser authUser) {
        final UserEntity user = securityUtils.getLoggedUser(authUser);
        if (userRepository.checkIfUserWithSameEmailExist(reqDto.getNewEmail(), user.getId())) {
            throw new EmailAddressAlreadyExistException(reqDto.getNewEmail());
        }
        user.setEmailAddress(reqDto.getNewEmail());
        userRepository.save(user);

        log.info("Successfully updated email from '{}' to '{}'. User: {}", user.getEmailAddress(),
            reqDto.getNewEmail(), user.getLogin());

        return UpdatedEmailResDto.builder()
            .message(messageService.getMessage(AppLocaleSet.NEW_EMAIL_ADDRESS_SET_RES,
                Map.of("login", user.getLogin())))
            .newEmail(reqDto.getNewEmail())
            .build();
    }

    @Override
    public SimpleMessageResDto updatePassword(UpdatePasswordReqDto reqDto, AuthUser authUser) {
        final UserEntity user = userRepository.findUserByLoginOrEmail(authUser.getUsername())
            .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(reqDto.getOldPassword(), user.getPassword())) {
            throw new PassedCredentialsNotValidException(user.getLogin());
        }
        user.setPassword(passwordEncoder.encode(reqDto.getNewPassword()));
        userRepository.save(user);

        final MailRequestDto requestDto = smtpUtils.createBaseMailRequest(user, AppLocaleSet.CHANGE_PASSWORD_TITLE_MAIL);
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));
        jmpslMailService.sendEmail(requestDto, parameters, MailTemplate.CHANGE_PASSOWRD);

        log.info("Successfully updated password for user. User: {}", user.getLogin());
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.NEW_PASSWORD_SET_RES,
            Map.of("login", user.getLogin())));
    }

    @Override
    public SimpleMessageResDto delete(DeleteAccountReqDto reqDto, AuthUser authUser) {
        final UserEntity user = userRepository.findUserByLoginOrEmail(authUser.getUsername())
            .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(reqDto.getPassword(), user.getPassword())) {
            throw new PassedCredentialsNotValidException(user.getLogin());
        }
        userRepository.delete(user);

        final MailRequestDto requestDto = smtpUtils.createBaseMailRequest(user, AppLocaleSet.DELETE_ACCOUNT_TITLE_MAIL);
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));
        jmpslMailService.sendEmail(requestDto, parameters, MailTemplate.DELETE_ACCOUNT);

        log.info("Successfully deleted user account. Deleted user account: {}", user);
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.REMOVE_ACCOUNT_RES,
            Map.of("login", user.getLogin())));
    }
}
