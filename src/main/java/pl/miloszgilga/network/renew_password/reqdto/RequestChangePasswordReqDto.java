/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: RequestChangePasswordReqDto.java
 * Last modified: 17/05/2023, 16:25
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

package pl.miloszgilga.network.renew_password.reqdto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

import pl.miloszgilga.validator.Regex;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Data
@NoArgsConstructor
public class RequestChangePasswordReqDto {

    @NotBlank(message = "jpa.validator.loginOrEmail.notBlank")
    @Pattern(regexp = Regex.LOGIN_EMAIL, message = "jpa.validator.loginOrEmail.regex")
    private String loginOrEmail;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "loginOrEmail=" + loginOrEmail +
            '}';
    }
}
