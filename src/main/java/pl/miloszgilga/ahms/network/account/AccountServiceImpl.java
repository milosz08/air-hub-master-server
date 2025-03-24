package pl.miloszgilga.ahms.network.account;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.algorithms.GameAlgorithms;
import pl.miloszgilga.ahms.algorithms.LevelBoostRangeDto;
import pl.miloszgilga.ahms.domain.blacklist_jwt.BlacklistJwtEntity;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.domain.user.UserRepository;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.exception.rest.AccountException;
import pl.miloszgilga.ahms.exception.rest.AuthException;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;
import pl.miloszgilga.ahms.mail.MailRequestDto;
import pl.miloszgilga.ahms.mail.MailService;
import pl.miloszgilga.ahms.mail.MailTemplate;
import pl.miloszgilga.ahms.network.account.reqdto.*;
import pl.miloszgilga.ahms.network.account.resdto.AccountDetailsResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.ahms.network.account.resdto.UpdatedNameResDto;
import pl.miloszgilga.ahms.security.LoggedUser;
import pl.miloszgilga.ahms.security.jwt.JwtService;
import pl.miloszgilga.ahms.utils.Utilities;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class AccountServiceImpl implements AccountService {
    private final JwtService jwtService;
    private final GameAlgorithms gameAlgorithms;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final LocaleMessageService localeMessageService;

    private final UserRepository userRepository;

    @Override
    public AccountDetailsResDto details(LoggedUser loggedUser) {
        final UserEntity user = loggedUser.userEntity();
        final LevelBoostRangeDto levelBoostRange = gameAlgorithms.getLevelBoostRange(user.getLevel());
        return AccountDetailsResDto.builder()
            .id(user.getId())
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
    public UpdatedNameResDto updateName(UpdateNameReqDto reqDto, LoggedUser loggedUser) {
        final UserEntity user = loggedUser.userEntity();
        user.setFirstName(StringUtils.capitalize(reqDto.getFirstName()));
        user.setLastName(StringUtils.capitalize(reqDto.getLastName()));
        userRepository.save(user);

        log.info("Successfully updated first and last name from '{}' to '{}'. User: {}", user, reqDto,
            user.getLogin());

        return UpdatedNameResDto.builder()
            .message(localeMessageService.getMessage(AppLocaleSet.NEW_NAME_SET_RES,
                Map.of("login", user.getLogin())))
            .newFirstName(StringUtils.capitalize(reqDto.getFirstName()))
            .newLastName(StringUtils.capitalize(reqDto.getLastName()))
            .build();
    }

    @Override
    public UpdatedLoginResDto updateLogin(HttpServletRequest req, UpdateLoginReqDto reqDto, LoggedUser loggedUser) {
        final String extractedToken = jwtService.extractToken(req);
        final UserEntity user = loggedUser.userEntity();
        if (userRepository.checkIfUserWithSameLoginExist(reqDto.getNewLogin(), user.getId())) {
            throw new AccountException.LoginAlreadyExistException(reqDto.getNewLogin());
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
            .message(localeMessageService.getMessage(AppLocaleSet.NEW_EMAIL_ADDRESS_SET_RES,
                Map.of("login", user.getLogin())))
            .newLogin(reqDto.getNewLogin())
            .updatedJwt(jwtService.generateToken(savedUser))
            .build();
    }

    @Override
    public UpdatedEmailResDto updateEmail(UpdateEmailReqDto reqDto, LoggedUser loggedUser) {
        final UserEntity user = loggedUser.userEntity();
        if (userRepository.checkIfUserWithSameEmailExist(reqDto.getNewEmail(), user.getId())) {
            throw new AccountException.EmailAddressAlreadyExistException(reqDto.getNewEmail());
        }
        user.setEmailAddress(reqDto.getNewEmail());
        userRepository.save(user);

        log.info("Successfully updated email from '{}' to '{}'. User: {}", user.getEmailAddress(),
            reqDto.getNewEmail(), user.getLogin());

        return UpdatedEmailResDto.builder()
            .message(localeMessageService.getMessage(AppLocaleSet.NEW_EMAIL_ADDRESS_SET_RES,
                Map.of("login", user.getLogin())))
            .newEmail(reqDto.getNewEmail())
            .build();
    }

    @Override
    public SimpleMessageResDto updatePassword(UpdatePasswordReqDto reqDto, LoggedUser loggedUser) {
        final UserEntity user = userRepository.findUserByLoginOrEmail(loggedUser.getUsername())
            .orElseThrow(AuthException.UserNotFoundException::new);

        if (!passwordEncoder.matches(reqDto.getOldPassword(), user.getPassword())) {
            throw new AccountException.PassedCredentialsNotValidException(user.getLogin());
        }
        user.setPassword(passwordEncoder.encode(reqDto.getNewPassword()));
        userRepository.save(user);

        final MailRequestDto requestDto = mailService.createBaseMailRequest(user, AppLocaleSet.CHANGE_PASSWORD_TITLE_MAIL);
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));
        mailService.sendEmail(requestDto, parameters, MailTemplate.CHANGE_PASSOWRD);

        log.info("Successfully updated password for user. User: {}", user.getLogin());
        return new SimpleMessageResDto(localeMessageService.getMessage(AppLocaleSet.NEW_PASSWORD_SET_RES,
            Map.of("login", user.getLogin())));
    }

    @Override
    public SimpleMessageResDto delete(DeleteAccountReqDto reqDto, LoggedUser loggedUser) {
        final UserEntity user = userRepository.findUserByLoginOrEmail(loggedUser.getUsername())
            .orElseThrow(AuthException.UserNotFoundException::new);

        if (!passwordEncoder.matches(reqDto.getPassword(), user.getPassword())) {
            throw new AccountException.PassedCredentialsNotValidException(user.getLogin());
        }
        userRepository.delete(user);

        final MailRequestDto requestDto = mailService.createBaseMailRequest(user, AppLocaleSet.DELETE_ACCOUNT_TITLE_MAIL);
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));
        mailService.sendEmail(requestDto, parameters, MailTemplate.DELETE_ACCOUNT);

        log.info("Successfully deleted user account. Deleted user account: {}", user);
        return new SimpleMessageResDto(localeMessageService.getMessage(AppLocaleSet.REMOVE_ACCOUNT_RES,
            Map.of("login", user.getLogin())));
    }
}
