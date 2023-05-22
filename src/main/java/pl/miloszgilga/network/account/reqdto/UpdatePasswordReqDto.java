/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: UpdatePasswordReqDto.java
 * Last modified: 21/05/2023, 21:09
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

package pl.miloszgilga.network.account.reqdto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

import org.jmpsl.core.validator.IPasswordValidatorModel;
import org.jmpsl.core.validator.ValidateMatchingPasswords;

import pl.miloszgilga.validator.Regex;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Data
@NoArgsConstructor
@ValidateMatchingPasswords(message = "jpa.validator.confirmedPassword.notMatch")
public class UpdatePasswordReqDto implements IPasswordValidatorModel {

    @NotBlank(message = "jpa.validator.oldPassword.notBlank")
    private String oldPassword;

    @NotBlank(message = "jpa.validator.password.notBlank")
    @Pattern(regexp = Regex.PASSWORD_REQUIREMENTS, message = "jpa.validator.password.regex")
    private String newPassword;

    @NotBlank(message = "jpa.validator.confirmedPassword.notBlank")
    private String confirmedNewPassword;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override public String getPassword()               { return newPassword; }
    @Override public String getConfirmedPassword()      { return confirmedNewPassword; }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "oldPassword=" + oldPassword +
            ", newPassword=" + newPassword +
            ", confirmedNewPassword=" + confirmedNewPassword +
            '}';
    }
}
