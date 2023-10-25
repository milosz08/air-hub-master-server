/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.renew_password;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.jmpsl.communication.mail.JmpslMailService;
import org.jmpsl.communication.mail.MailRequestDto;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.security.OtaTokenService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.miloszgilga.config.ApiProperties;
import pl.miloszgilga.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.domain.ota_token.OtaTokenRepository;
import pl.miloszgilga.domain.ota_token.OtaTokenType;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.UserRepository;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.network.renew_password.reqdto.ChangePasswordValidatorReqDto;
import pl.miloszgilga.network.renew_password.reqdto.RequestChangePasswordReqDto;
import pl.miloszgilga.utils.MailTemplate;
import pl.miloszgilga.utils.Utilities;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static pl.miloszgilga.exception.AuthException.OtaTokenNotFoundException;
import static pl.miloszgilga.exception.AuthException.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
class RenewPasswordServiceImpl implements RenewPasswordService {
    private final ApiProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final OtaTokenService otaTokenService;
    private final JmpslMailService jmpslMailService;
    private final LocaleMessageService messageService;

    private final UserRepository userRepository;
    private final OtaTokenRepository otaTokenRepository;

    @Override
    public SimpleMessageResDto request(RequestChangePasswordReqDto reqDto) {
        final UserEntity user = userRepository.findUserByLoginOrEmail(reqDto.getLoginOrEmail())
            .orElseThrow(UserNotFoundException::new);

        String token;
        final Date expiredAt = DateUtils.addMinutes(Date.from(Instant.now()), properties.getOtaExpiredPasswordMinutes());
        do {
            token = otaTokenService.generateToken();
        } while (otaTokenRepository.checkIfTokenAlreadyExist(token));

        final OtaTokenEntity otaToken = OtaTokenEntity.builder()
            .token(token)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .type(OtaTokenType.RESET_PASSWORD)
            .isUsed(false)
            .build();

        final MailRequestDto requestDto = MailRequestDto.builder()
            .sendTo(Set.of(user.getEmailAddress()))
            .sendFrom(properties.getMailResponder())
            .replyAddress(properties.getReplyResponder())
            .messageSubject(messageService.getMessage(AppLocaleSet.REQUEST_CHANGE_PASSWORD_TITLE_MAIL, Map.of(
                "appName", properties.getAppName(),
                "userLogin", user.getLogin()
            )))
            .appName(properties.getAppName())
            .locale(LocaleContextHolder.getLocale())
            .build();

        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));
        parameters.put("token", token);
        parameters.put("expirationMinutes", String.valueOf(properties.getOtaExpiredPasswordMinutes()));

        jmpslMailService.sendEmail(requestDto, parameters, MailTemplate.REQUEST_CHANGE_PASSWORD);

        user.persistOtaToken(otaToken);
        userRepository.save(user);

        log.info("Request for change password for user was successfully sended. User data: {}", user);
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.RESET_PASSWORD_REQUEST_RES,
            Map.of("email", user.getEmailAddress())));
    }

    @Override
    public SimpleMessageResDto change(ChangePasswordValidatorReqDto reqDto) {
        final OtaTokenEntity otaToken = otaTokenRepository
            .findTokenByType(reqDto.getToken(), OtaTokenType.RESET_PASSWORD)
            .orElseThrow(() -> new OtaTokenNotFoundException(reqDto.getToken()));

        final UserEntity user = otaToken.getUser();
        user.setPassword(passwordEncoder.encode(reqDto.getPassword()));
        otaToken.setUsed(true);
        otaTokenRepository.save(otaToken);

        log.info("Password for user was successfully changed. User data: {}", user);
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.RESET_PASSWORD_CHANGE_RES));
    }
}
