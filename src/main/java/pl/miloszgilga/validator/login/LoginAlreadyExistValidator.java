/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: LoginAlreadyExistValidator.java
 * Last modified: 18/05/2023, 18:23
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

package pl.miloszgilga.validator.login;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import pl.miloszgilga.domain.user.IUserRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Component
@RequiredArgsConstructor
class LoginAlreadyExistValidator implements ConstraintValidator<ValidateAlreadyExistingLogin, String> {

    private final IUserRepository userRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        if (userRepository.checkIfUserAlreadyExist(login)) {
            log.error("Attempt to create user with existing login. Existing login {}", login);
            return false;
        }
        return true;
    }
}
