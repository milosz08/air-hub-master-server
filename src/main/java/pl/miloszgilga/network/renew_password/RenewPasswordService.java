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

import org.springframework.stereotype.Service;

import pl.miloszgilga.dto.SimpleMessageResDto;

import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.ota_token.IOtaTokenRepository;

import pl.miloszgilga.network.renew_password.reqdto.RequestChangePasswordReqDto;
import pl.miloszgilga.network.renew_password.reqdto.ChangePasswordValidatorReqDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Service
public class RenewPasswordService implements IRenewPasswordService {

    private final IUserRepository userRepository;
    private final IOtaTokenRepository otaTokenRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    RenewPasswordService(IUserRepository userRepository, IOtaTokenRepository otaTokenRepository) {
        this.userRepository = userRepository;
        this.otaTokenRepository = otaTokenRepository;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public SimpleMessageResDto requestChangePassword(RequestChangePasswordReqDto reqDto) {
        return null;
    }

    @Override
    public SimpleMessageResDto changePassword(ChangePasswordValidatorReqDto reqDto) {
        return null;
    }
}
