/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game.reqdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GenerateStatsReqDto {

    @NotNull(message = "jpa.validator.planeId.notNull")
    private Long planeId;

    @NotNull(message = "jpa.validator.crew.notNull")
    @Size(min = 3, message = "jpa.validator.crew.size")
    private List<Long> crew;

    @NotNull(message = "jpa.validator.routeId.notNull")
    private Long routeId;

    @Override
    public String toString() {
        return "{" +
            "planeId=" + planeId +
            ", crew=" + crew +
            ", routeId=" + routeId +
            '}';
    }
}
