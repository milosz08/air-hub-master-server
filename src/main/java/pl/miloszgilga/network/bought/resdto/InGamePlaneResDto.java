/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.bought.resdto;

import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;

import java.util.Objects;

public record InGamePlaneResDto(
    String planeName,
    String categoryName,
    int landingGeer,
    int wings,
    int engine,
    int upgrade,
    boolean isAvailable
) {
    public InGamePlaneResDto(InGamePlaneParamEntity entity) {
        this(entity.getPlane().getName(), entity.getPlane().getCategory().getName(), entity.getLandingGeer(),
            entity.getWings(), entity.getEngine(), entity.getUpgrade(), Objects.isNull(entity.getAvailable()));
    }
}
