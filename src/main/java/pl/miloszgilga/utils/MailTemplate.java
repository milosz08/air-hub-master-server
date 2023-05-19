/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: MailTemplate.java
 * Last modified: 19/05/2023, 14:29
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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.jmpsl.communication.mail.IMailEnumeratedTemplate;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Getter
@RequiredArgsConstructor
public enum MailTemplate implements IMailEnumeratedTemplate {
    ACTIVATED_ACCOUNT               ("/activated-account.template.ftl"),
    REGISTER                        ("/register.template.ftl"),
    REQUEST_CHANGE_PASSWORD         ("/request-change-password.template.ftl");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final String templateName;
}
