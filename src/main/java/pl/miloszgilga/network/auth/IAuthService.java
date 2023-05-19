/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IAuthService.java
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

import jakarta.servlet.http.HttpServletRequest;

import org.jmpsl.security.user.AuthUser;

import pl.miloszgilga.dto.SimpleMessageResDto;

import pl.miloszgilga.network.auth.reqdto.RefreshReqDto;
import pl.miloszgilga.network.auth.resdto.JwtAuthenticationResDto;
import pl.miloszgilga.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.network.auth.reqdto.ActivateAccountReqDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public interface IAuthService {
    JwtAuthenticationResDto login(LoginReqDto reqDto);
    SimpleMessageResDto register(RegisterReqDto reqDto);
    SimpleMessageResDto activate(ActivateAccountReqDto reqDto);
    JwtAuthenticationResDto refresh(RefreshReqDto reqDto);
    SimpleMessageResDto logout(HttpServletRequest req, AuthUser authUser);
}
