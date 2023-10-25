/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game.resdto;

import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.network.game.dto.CrewInFlightDto;

import java.time.LocalDateTime;
import java.util.List;

public record InFlightPlaneResDto(
    String name,
    String categoryName,
    LocalDateTime arrival,
    List<CrewInFlightDto> workers
) {
    public InFlightPlaneResDto(InGamePlaneParamEntity plane, List<CrewInFlightDto> workers) {
        this(plane.getPlane().getName(), plane.getPlane().getCategory().getName(), plane.getAvailable(), workers);
    }
}
