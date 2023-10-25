/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.auth.reqdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmpsl.core.validator.IPasswordValidatorModel;
import org.jmpsl.core.validator.ValidateMatchingPasswords;
import pl.miloszgilga.validator.Regex;
import pl.miloszgilga.validator.email.ValidateAlreadyExistingEmail;
import pl.miloszgilga.validator.login.ValidateAlreadyExistingLogin;

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
    @ValidateAlreadyExistingLogin(message = "jpa.validator.login.alreadyExist")
    private String login;

    @NotBlank(message = "jpa.validator.email.notBlank")
    @Email(message = "jpa.validator.email.regex")
    @ValidateAlreadyExistingEmail(message = "jpa.validator.email.alreadyExist")
    private String emailAddress;

    @NotBlank(message = "jpa.validator.password.notBlank")
    @Pattern(regexp = Regex.PASSWORD_REQUIREMENTS, message = "jpa.validator.password.regex")
    private String password;

    @NotBlank(message = "jpa.validator.confirmedPassword.notBlank")
    private String confirmedPassword;

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
