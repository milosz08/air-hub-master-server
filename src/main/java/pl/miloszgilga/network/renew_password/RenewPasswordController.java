/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: RenewPasswordController.java
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

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.miloszgilga.dto.SimpleMessageResDto;

import pl.miloszgilga.network.renew_password.reqdto.RequestChangePasswordReqDto;
import pl.miloszgilga.network.renew_password.reqdto.ChangePasswordValidatorReqDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/renew-password")
public class RenewPasswordController {

    private final IRenewPasswordService renewPasswordService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/request")
    ResponseEntity<SimpleMessageResDto> request(@RequestBody @Valid RequestChangePasswordReqDto reqDto) {
        return new ResponseEntity<>(renewPasswordService.request(reqDto), HttpStatus.OK);
    }

    @PostMapping("/change")
    ResponseEntity<SimpleMessageResDto> change(@RequestBody @Valid ChangePasswordValidatorReqDto reqDto) {
        return new ResponseEntity<>(renewPasswordService.change(reqDto), HttpStatus.OK);
    }
}
