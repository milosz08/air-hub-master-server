/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: BaseWithUserTest.java
 * Last modified: 27/05/2023, 01:13
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

package pl.miloszgilga;

import org.springframework.security.crypto.password.PasswordEncoder;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.security.GrantedUserRole;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public abstract class AbstractBaseTest {

    protected final PasswordEncoder passwordEncoder;

    protected AbstractBaseTest(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public UserEntity createBasicUserEntity(GrantedUserRole role, boolean isActivated) {
        return UserEntity.builder()
            .firstName("Jan")
            .lastName("Kowalski")
            .login("jankowalski123")
            .password(passwordEncoder.encode("SekretneHaslo123@"))
            .emailAddress("gilgamilosz451@gmail.com")
            .role(role)
            .isActivated(isActivated)
            .build();
    }

    public UserEntity createBasicUserEntity(GrantedUserRole role) {
        return createBasicUserEntity(role, true);
    }
}
