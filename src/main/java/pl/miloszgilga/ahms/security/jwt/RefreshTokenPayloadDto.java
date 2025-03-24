package pl.miloszgilga.ahms.security.jwt;

import java.time.ZonedDateTime;

public record RefreshTokenPayloadDto(String token, ZonedDateTime expiredDate) {
}
