/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.algorithms;

public record BoostLevelDto(
    long fromLevel,
    long toLevel,
    byte nextLevel,
    boolean isBoosted
) {
}
