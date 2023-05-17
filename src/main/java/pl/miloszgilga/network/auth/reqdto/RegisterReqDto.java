/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: RegisterReqDto.java
 * Last modified: 17/05/2023, 16:13
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

package pl.miloszgilga.network.auth.reqdto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

import org.jmpsl.core.validator.IPasswordValidatorModel;
import org.jmpsl.core.validator.ValidateMatchingPasswords;

import pl.miloszgilga.validator.Regex;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Data
@NoArgsConstructor
@ValidateMatchingPasswords(message = "jpa.validator.confirmedPassword.notMatch")
public class RegisterReqDto implements IPasswordValidatorModel {

    @NotBlank(message = "jpa.validator.firstName.notBlank")
    @Size(min = 2, max = 70, message = "jpa.validator.firstName.length")
    private String firstName;

    @NotBlank(message = "jpa.validator.lastName.notBlank")
    @Size(min = 2, max = 70, message = "jpa.validator.lastName.length")
    private String lastName;

    @NotBlank(message = "jpa.validator.login.notBlank")
    @Pattern(regexp = Regex.LOGIN, message = "jpa.validator.login.regex")
    private String login;

    @NotBlank(message = "jpa.validator.email.notBlank")
    @Email(message = "jpa.validator.email.regex")
    private String emailAddress;

    @NotBlank(message = "jpa.validator.password.notBlank")
    @Pattern(regexp = Regex.PASSWORD_REQUIREMENTS, message = "jpa.validator.password.regex")
    private String password;

    @NotBlank(message = "jpa.validator.confirmedPassword.notBlank")
    private String confirmedPassword;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "firstName=" + firstName +
            ", lastName=" + lastName +
            ", login=" + login +
            ", emailAddress=" + emailAddress +
            ", password=" + password +
            ", confirmPassword=" + confirmedPassword +
            '}';
    }
}
