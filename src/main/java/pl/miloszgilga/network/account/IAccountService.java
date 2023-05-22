/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: IUserDetailsService.java
 * Last modified: 21/05/2023, 20:33
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

package pl.miloszgilga.network.account;

import jakarta.servlet.http.HttpServletRequest;

import org.jmpsl.security.user.AuthUser;
import pl.miloszgilga.dto.SimpleMessageResDto;

import pl.miloszgilga.network.account.reqdto.*;
import pl.miloszgilga.network.account.resdto.UpdatedNameResDto;
import pl.miloszgilga.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.network.account.resdto.AccountDetailsResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public interface IAccountService {
    AccountDetailsResDto details(AuthUser authUser);
    UpdatedNameResDto updateName(UpdateNameReqDto reqDto, AuthUser authUser);
    UpdatedLoginResDto updateLogin(HttpServletRequest req, UpdateLoginReqDto reqDto, AuthUser authUser);
    UpdatedEmailResDto updateEmail(UpdateEmailReqDto reqDto, AuthUser authUser);
    SimpleMessageResDto updatePassword(UpdatePasswordReqDto reqDto, AuthUser authUser);
    SimpleMessageResDto delete(DeleteAccountReqDto reqDto, AuthUser authUser);
}
