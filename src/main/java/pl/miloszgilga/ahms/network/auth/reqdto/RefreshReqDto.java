package pl.miloszgilga.ahms.network.auth.reqdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshReqDto {
    @NotBlank(message = "jpa.validator.expierdToken.notBlank")
    private String expiredJwt;

    @NotBlank(message = "jpa.validator.refreshToken.notBlank")
    private String refreshToken;

    @Override
    public String toString() {
        return "{" +
            "expiredJwt='" + expiredJwt +
            ", refreshToken='" + refreshToken +
            '}';
    }
}
