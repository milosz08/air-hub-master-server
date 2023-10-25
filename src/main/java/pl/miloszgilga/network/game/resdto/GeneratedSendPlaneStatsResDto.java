/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.game.resdto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GeneratedSendPlaneStatsResDto(
    int currentLevel,
    long fromLevel,
    long toLevel,
    int nextLevel,
    long currentExp,
    int maxLevels,
    boolean isUpgraded,
    int addedExp,
    int prize,
    int totalCost,
    long accountDeposit,
    LocalDateTime arrival
) {
}
