/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.auth.reqdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.miloszgilga.validator.Regex;

@Data
@NoArgsConstructor
public class ActivateAccountReqDto {

    @NotBlank(message = "jpa.validator.token.notBlank")
    @Pattern(regexp = Regex.OTA_TOKEN, message = "jpa.validator.token.regex")
    private String token;

    @Override
    public String toString() {
        return "{" +
            "token=" + token +
            '}';
    }
}
