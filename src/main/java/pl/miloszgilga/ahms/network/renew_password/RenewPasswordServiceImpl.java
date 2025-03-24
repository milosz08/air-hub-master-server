package pl.miloszgilga.ahms.network.renew_password;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenRepository;
import pl.miloszgilga.ahms.domain.ota_token.OtaTokenType;
import pl.miloszgilga.ahms.domain.user.UserEntity;
import pl.miloszgilga.ahms.domain.user.UserRepository;
import pl.miloszgilga.ahms.dto.SimpleMessageResDto;
import pl.miloszgilga.ahms.exception.rest.AuthException;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;
import pl.miloszgilga.ahms.mail.MailRequestDto;
import pl.miloszgilga.ahms.mail.MailService;
import pl.miloszgilga.ahms.mail.MailTemplate;
import pl.miloszgilga.ahms.network.renew_password.reqdto.ChangePasswordValidatorReqDto;
import pl.miloszgilga.ahms.network.renew_password.reqdto.RequestChangePasswordReqDto;
import pl.miloszgilga.ahms.security.ota.OtaProperties;
import pl.miloszgilga.ahms.security.ota.OtaService;
import pl.miloszgilga.ahms.utils.Utilities;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class RenewPasswordServiceImpl implements RenewPasswordService {
    private final PasswordEncoder passwordEncoder;
    private final OtaService otaService;
    private final OtaProperties otaProperties;
    private final MailService mailService;
    private final LocaleMessageService localeMessageService;

    private final UserRepository userRepository;
    private final OtaTokenRepository otaTokenRepository;

    @Override
    public SimpleMessageResDto request(RequestChangePasswordReqDto reqDto) {
        final UserEntity user = userRepository.findUserByLoginOrEmail(reqDto.getLoginOrEmail())
            .orElseThrow(AuthException.UserNotFoundException::new);

        String token;
        final Date expiredAt = DateUtils.addSeconds(Date.from(Instant.now()), otaProperties.getRenewPasswordExpiredSec());
        do {
            token = otaService.generateToken();
        } while (otaTokenRepository.checkIfTokenAlreadyExist(token));

        final OtaTokenEntity otaToken = OtaTokenEntity.builder()
            .token(token)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .type(OtaTokenType.RESET_PASSWORD)
            .isUsed(false)
            .build();

        final Duration expiredAtDuration = Duration.ofSeconds(otaProperties.getRenewPasswordExpiredSec());
        final MailRequestDto requestDto = mailService
            .createBaseMailRequest(user, AppLocaleSet.REQUEST_CHANGE_PASSWORD_TITLE_MAIL);

        final Map<String, String> parameters = new HashMap<>();
        parameters.put("fullName", Utilities.parseFullName(user));
        parameters.put("token", token);
        parameters.put("expirationMinutes", String.valueOf(expiredAtDuration.get(ChronoUnit.MINUTES)));

        mailService.sendEmail(requestDto, parameters, MailTemplate.REQUEST_CHANGE_PASSWORD);

        user.persistOtaToken(otaToken);
        userRepository.save(user);

        log.info("Request for change password for user was successfully sended. User data: {}", user);
        return new SimpleMessageResDto(localeMessageService.getMessage(AppLocaleSet.RESET_PASSWORD_REQUEST_RES,
            Map.of("email", user.getEmailAddress())));
    }

    @Override
    public SimpleMessageResDto change(ChangePasswordValidatorReqDto reqDto) {
        final OtaTokenEntity otaToken = otaTokenRepository
            .findTokenByType(reqDto.getToken(), OtaTokenType.RESET_PASSWORD)
            .orElseThrow(() -> new AuthException.OtaTokenNotFoundException(reqDto.getToken()));

        final UserEntity user = otaToken.getUser();
        user.setPassword(passwordEncoder.encode(reqDto.getPassword()));
        otaToken.setIsUsed(true);
        otaTokenRepository.save(otaToken);

        log.info("Password for user was successfully changed. User data: {}", user);
        return new SimpleMessageResDto(localeMessageService.getMessage(AppLocaleSet.RESET_PASSWORD_CHANGE_RES));
    }
}
