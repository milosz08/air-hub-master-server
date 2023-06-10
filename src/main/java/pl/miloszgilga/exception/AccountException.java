/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: AccountException.java
 * Last modified: 22/05/2023, 00:31
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

package pl.miloszgilga.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.jmpsl.core.exception.RestServiceServerException;

import pl.miloszgilga.i18n.AppLocaleSet;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class AccountException {

    @Slf4j public static class LoginAlreadyExistException extends RestServiceServerException {
        public LoginAlreadyExistException(String newLogin) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.LOGIN_ALREADY_EXIST_EXC);
            log.error("Attempt to change login '{}' on account, when it is already existing on another account.",
                newLogin);
        }
    }

    @Slf4j public static class EmailAddressAlreadyExistException extends RestServiceServerException {
        public EmailAddressAlreadyExistException(String newEmail) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.EMAIL_ADDRESS_ALREADY_EXIST_EXC);
            log.error("Attempt to change email '{}' on account, when it is already existing on another account.",
                newEmail);
        }
    }
}
