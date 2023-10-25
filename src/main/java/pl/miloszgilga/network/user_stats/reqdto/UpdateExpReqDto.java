/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.user_stats.reqdto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateExpReqDto {

    @NotNull(message = "jpa.validator.exp.notBlank")
    @Min(value = 0, message = "jpa.validator.exp.min")
    @Max(value = 250_000_000, message = "jpa.validator.exp.max")
    private Integer exp;

    @Override
    public String toString() {
        return "{" +
            "exp=" + exp +
            '}';
    }
}
