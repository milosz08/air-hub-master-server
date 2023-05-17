/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: AuthService.java
 * Last modified: 17/05/2023, 16:05
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

package pl.miloszgilga.network.auth;

import org.springframework.stereotype.Service;
import org.jmpsl.core.i18n.LocaleMessageService;

import pl.miloszgilga.dto.SimpleMessageResDto;

import pl.miloszgilga.network.AbstractRestService;
import pl.miloszgilga.network.auth.resdto.LoginResDto;
import pl.miloszgilga.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.network.auth.reqdto.ActivateAccountReqDto;

import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.ota_token.IOtaTokenRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Service
public class AuthService extends AbstractRestService implements IAuthService {

    private final IUserRepository userRepository;
    private final IOtaTokenRepository otaTokenRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    AuthService(
        LocaleMessageService localeMessageService, IUserRepository userRepository,
        IOtaTokenRepository otaTokenRepository
    ) {
        super(localeMessageService);
        this.userRepository = userRepository;
        this.otaTokenRepository = otaTokenRepository;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public LoginResDto login(LoginReqDto reqDto) {
        return null;
    }

    @Override
    public SimpleMessageResDto register(RegisterReqDto reqDto) {
        return null;
    }

    @Override
    public SimpleMessageResDto activateAccount(ActivateAccountReqDto reqDto) {
        return null;
    }

    @Override
    public SimpleMessageResDto logout() {
        return null;
    }
}
