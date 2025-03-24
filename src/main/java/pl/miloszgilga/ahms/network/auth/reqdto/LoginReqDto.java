package pl.miloszgilga.ahms.network.auth.reqdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginReqDto {
    @NotBlank(message = "jpa.validator.loginOrEmail.notBlank")
    private String loginOrEmail;

    @NotBlank(message = "jpa.validator.password.notBlank")
    private String password;

    @Override
    public String toString() {
        return "{" +
            "loginOrEmail=" + loginOrEmail +
            ", password=" + password +
            '}';
    }
}
