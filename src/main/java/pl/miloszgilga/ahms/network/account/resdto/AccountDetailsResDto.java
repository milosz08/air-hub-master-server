package pl.miloszgilga.ahms.network.account.resdto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AccountDetailsResDto(
    long id,
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
