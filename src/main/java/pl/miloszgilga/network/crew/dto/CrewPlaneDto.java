/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.crew.dto;

import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

public record CrewPlaneDto(
    Long id,
    String planeName,
    String categoryName
) {
    public CrewPlaneDto(InGamePlaneParamEntity plane) {
        this(plane.getId(), plane.getPlane().getName(), plane.getPlane().getCategory().getName());
    }
}
