/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.utils;

import lombok.RequiredArgsConstructor;
import org.jmpsl.communication.mail.MailRequestDto;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import pl.miloszgilga.config.ApiProperties;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.i18n.AppLocaleSet;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SmtpUtils {
    private final ApiProperties properties;
    private final LocaleMessageService messageService;

    public MailRequestDto createBaseMailRequest(UserEntity user, AppLocaleSet appLocaleSet) {
        return MailRequestDto.builder()
            .sendTo(Set.of(user.getEmailAddress()))
            .sendFrom(properties.getMailResponder())
            .replyAddress(properties.getReplyResponder())
            .messageSubject(messageService.getMessage(appLocaleSet, Map.of(
                "appName", properties.getAppName(),
                "userLogin", user.getLogin()
            )))
            .appName(properties.getAppName())
            .locale(LocaleContextHolder.getLocale())
            .build();
    }
}
