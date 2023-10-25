/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game.resdto;

import lombok.Builder;
import pl.miloszgilga.network.game.dto.PlaneWithRoutesDto;

import java.util.List;

@Builder
public record PlanesWithRoutesResDto(
    List<String> planesCategories,
    List<PlaneWithRoutesDto> planes
) {
}
