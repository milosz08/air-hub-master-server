package pl.miloszgilga.ahms.network.game.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PlaneWithRoutesDto(
    long planeId,
    String planeName,
    String planeCategory,
    int level,
    List<RouteDto> routes,
    List<Long> crew
) {
}
