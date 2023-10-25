/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game.dto;

import lombok.Builder;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.domain.plane_route.PlaneRouteEntity;

import java.util.List;

@Builder
public record PlaneWithWorkersAndRouteDto(
    InGamePlaneParamEntity plane,
    List<InGameWorkerParamEntity> workers,
    PlaneRouteEntity route
) {
}
