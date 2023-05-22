/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: UserDetailsController.java
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

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;

import pl.miloszgilga.dto.SimpleMessageResDto;

import pl.miloszgilga.network.account.reqdto.*;
import pl.miloszgilga.network.account.resdto.UpdatedNameResDto;
import pl.miloszgilga.network.account.resdto.UpdatedLoginResDto;
import pl.miloszgilga.network.account.resdto.UpdatedEmailResDto;
import pl.miloszgilga.network.account.resdto.AccountDetailsResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/account")
public class AccountController {

    private final IAccountService accountService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/details")
    ResponseEntity<AccountDetailsResDto> details(@CurrentUser AuthUser authUser) {
        return new ResponseEntity<>(accountService.details(authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/name")
    ResponseEntity<UpdatedNameResDto> updateName(
        @RequestBody @Valid UpdateNameReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updateName(reqDto, authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/login")
    ResponseEntity<UpdatedLoginResDto> updateLogin(
        HttpServletRequest req, @RequestBody @Valid UpdateLoginReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updateLogin(req, reqDto, authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/email")
    ResponseEntity<UpdatedEmailResDto> updateEmail(
        @RequestBody @Valid UpdateEmailReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updateEmail(reqDto, authUser), HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    ResponseEntity<SimpleMessageResDto> updatePassword(
        @RequestBody @Valid UpdatePasswordReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.updatePassword(reqDto, authUser), HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<SimpleMessageResDto> delete(
        @RequestBody @Valid DeleteAccountReqDto reqDto, @CurrentUser AuthUser authUser
    ) {
        return new ResponseEntity<>(accountService.delete(reqDto, authUser), HttpStatus.OK);
    }
}
