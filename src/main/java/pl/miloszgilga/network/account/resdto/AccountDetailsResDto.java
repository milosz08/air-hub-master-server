/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.account.resdto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AccountDetailsResDto(
    String firstName,
    String lastName,
    String login,
    String emailAddress,
    String role,
    Byte level,
    Integer exp,
    Long money,
    long fromLevel,
    long toLevel,
    ZonedDateTime accountCreated
) {
}
