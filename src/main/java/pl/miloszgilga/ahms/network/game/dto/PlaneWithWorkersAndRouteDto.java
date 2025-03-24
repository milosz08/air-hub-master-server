package pl.miloszgilga.ahms.network.game.dto;

import lombok.Builder;
import pl.miloszgilga.ahms.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.ahms.domain.in_game_worker_params.InGameWorkerParamEntity;
import pl.miloszgilga.ahms.domain.plane_route.PlaneRouteEntity;

import java.util.List;

@Builder
public record PlaneWithWorkersAndRouteDto(
    InGamePlaneParamEntity plane,
    List<InGameWorkerParamEntity> workers,
    PlaneRouteEntity route
) {
}
