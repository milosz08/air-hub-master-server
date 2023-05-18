/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: AuthUserDetailsService.java
 * Last modified: 17/05/2023, 20:19
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

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.jmpsl.security.SecurityUtil;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public UserDetails loadUserByUsername(String loginOrEmail) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findUserByLoginOrEmail(loginOrEmail).orElseThrow(() -> {
            log.error("Unable to load user with credentials data (login or email): {}", loginOrEmail);
            return new UsernameNotFoundException("Unable to load user in AuthUserDetailsService");
        });
        return SecurityUtil.fabricateUser(user);
    }
}
