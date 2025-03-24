package pl.miloszgilga.ahms.network.account.reqdto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteAccountReqDto {
    @NotNull(message = "jpa.validator.password.notBlank")
    private String password;

    @Override
    public String toString() {
        return "{" +
            "password=" + password +
            '}';
    }
}
