/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: SecurityUtils.java
 * Last modified: 22/05/2023, 00:44
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

package pl.miloszgilga.security;

import lombok.RequiredArgsConstructor;

import org.jmpsl.security.user.AuthUser;
import org.springframework.stereotype.Component;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;

import pl.miloszgilga.exception.AuthException.UserNotFoundException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final IUserRepository userRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public UserEntity getLoggedUser(AuthUser authUser) {
        return userRepository
            .findUserByLoginOrEmail(authUser.getUsername())
            .orElseThrow(UserNotFoundException::new);
    }
}
