package pl.miloszgilga.ahms.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtValidationType {
    MALFORMED("Passed JSON Web Token is malformed."),
    EXPIRED("Passed JSON Web Token is expired."),
    INVALID("Passed JSON Web Token is invalid."),
    OTHER("Some of the JSON Web Token claims are nullable."),
    GOOD("JSON Web Token is valid."),
    ;

    private final String message;
}
