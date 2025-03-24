package pl.miloszgilga.ahms.network.account.reqdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.miloszgilga.ahms.validator.Regex;
import pl.miloszgilga.ahms.validator.password.PasswordsMatchValidatorModel;
import pl.miloszgilga.ahms.validator.password.ValidateMatchingPasswords;

@Data
@NoArgsConstructor
@ValidateMatchingPasswords(message = "jpa.validator.confirmedPassword.notMatch")
public class UpdatePasswordReqDto implements PasswordsMatchValidatorModel {
    @NotBlank(message = "jpa.validator.oldPassword.notBlank")
    private String oldPassword;

    @NotBlank(message = "jpa.validator.password.notBlank")
    @Pattern(regexp = Regex.PASSWORD_REQUIREMENTS, message = "jpa.validator.password.regex")
    private String newPassword;

    @NotBlank(message = "jpa.validator.confirmedPassword.notBlank")
    private String confirmedNewPassword;

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
            "oldPassword=" + oldPassword +
            ", newPassword=" + newPassword +
            ", confirmedNewPassword=" + confirmedNewPassword +
            '}';
    }
}
