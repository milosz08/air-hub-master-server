package pl.miloszgilga.ahms.security.jwt;

import io.jsonwebtoken.Claims;

import java.util.Optional;

public record ValidateJwtPayload(
    JwtValidationType type,
    Optional<Claims> claims
) {
    public ValidateJwtPayload(JwtValidationType type) {
        this(type, Optional.empty());
    }
}
