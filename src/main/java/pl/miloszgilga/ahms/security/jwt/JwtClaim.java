package pl.miloszgilga.ahms.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtClaim {
    ID("ID"),
    LOGIN("LOGIN"),
    ROLE("ROLE");

    private final String name;
}
