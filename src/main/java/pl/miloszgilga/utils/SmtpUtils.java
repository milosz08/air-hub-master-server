/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: SmtpUtils.java
 * Last modified: 22/05/2023, 01:01
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

package pl.miloszgilga.utils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.context.i18n.LocaleContextHolder;

import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.communication.mail.MailRequestDto;

import java.util.Map;
import java.util.Set;

import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.config.ApiProperties;
import pl.miloszgilga.domain.user.UserEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Component
@RequiredArgsConstructor
public class SmtpUtils {

    private final ApiProperties properties;
    private final LocaleMessageService messageService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
