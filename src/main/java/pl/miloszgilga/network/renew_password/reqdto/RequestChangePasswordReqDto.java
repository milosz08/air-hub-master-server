/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.renew_password.reqdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.miloszgilga.validator.Regex;

@Data
@NoArgsConstructor
public class RequestChangePasswordReqDto {

    @NotBlank(message = "jpa.validator.loginOrEmail.notBlank")
    @Pattern(regexp = Regex.LOGIN_EMAIL, message = "jpa.validator.loginOrEmail.regex")
    private String loginOrEmail;

    @Override
    public String toString() {
        return "{" +
            "loginOrEmail=" + loginOrEmail +
            '}';
    }
}
