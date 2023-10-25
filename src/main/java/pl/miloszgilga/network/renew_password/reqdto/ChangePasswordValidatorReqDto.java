/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.renew_password.reqdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmpsl.core.validator.IPasswordValidatorModel;
import org.jmpsl.core.validator.ValidateMatchingPasswords;
import pl.miloszgilga.validator.Regex;

@Data
@NoArgsConstructor
@ValidateMatchingPasswords(message = "jpa.validator.confirmedPassword.notMatch")
public class ChangePasswordValidatorReqDto implements IPasswordValidatorModel {

    @NotBlank(message = "jpa.validator.password.notBlank")
    @Pattern(regexp = Regex.PASSWORD_REQUIREMENTS, message = "jpa.validator.password.regex")
    private String newPassword;

    @NotBlank(message = "jpa.validator.confirmedPassword.notBlank")
    private String confirmedNewPassword;

    @NotBlank(message = "jpa.validator.token.notBlank")
    @Pattern(regexp = Regex.OTA_TOKEN, message = "jpa.validator.token.regex")
    private String token;

    @Override
    public String getPassword() {
        return newPassword;
    }

    @Override
    public String getConfirmedPassword() {
        return confirmedNewPassword;
    }

    @Override
    public String toString() {
        return "{" +
            "newPassword='" + newPassword +
            ", confirmedNewPassword='" + confirmedNewPassword +
            ", token='" + token +
            '}';
    }
}
