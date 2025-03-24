package pl.miloszgilga.ahms.algorithms;

public record BoostLevelDto(
    long fromLevel,
    long toLevel,
    byte nextLevel,
    boolean isBoosted
) {
}
