/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.user_stats.reqdto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMoneyReqDto {

    @NotNull(message = "jpa.validator.money.notBlank")
    @Min(value = 0, message = "jpa.validator.money.min")
    private Long money;

    @Override
    public String toString() {
        return "{" +
            "money=" + money +
            '}';
    }
}
