/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: RenewPasswordService.java
 * Last modified: 17/05/2023, 16:15
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.network.renew_password;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.HashMap;
import java.time.Instant;
import java.time.ZonedDateTime;

import org.jmpsl.security.OtaTokenService;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.communication.mail.MailRequestDto;
import org.jmpsl.communication.mail.JmpslMailService;

import org.apache.commons.lang3.time.DateUtils;

import pl.miloszgilga.utils.Utilities;
import pl.miloszgilga.utils.MailTemplate;
import pl.miloszgilga.config.ApiProperties;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.dto.SimpleMessageResDto;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.ota_token.OtaTokenType;
import pl.miloszgilga.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.domain.ota_token.IOtaTokenRepository;

import pl.miloszgilga.network.renew_password.reqdto.RequestChangePasswordReqDto;
import pl.miloszgilga.network.renew_password.reqdto.ChangePasswordValidatorReqDto;

import static pl.miloszgilga.exception.AuthException.UserNotFoundException;
import static pl.miloszgilga.exception.AuthException.OtaTokenNotFoundException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class RenewPasswordService implements IRenewPasswordService {

    private final ApiProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final OtaTokenService otaTokenService;
    private final JmpslMailService jmpslMailService;
    private final LocaleMessageService messageService;

    private final IUserRepository userRepository;
    private final IOtaTokenRepository otaTokenRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            .build();

        final MailRequestDto requestDto  = MailRequestDto.builder()
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
