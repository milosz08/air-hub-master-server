package pl.miloszgilga.ahms.security.jwt;

public record JwtValidPayload(boolean isValid, JwtValidationType type) {
    public boolean checkType(JwtValidationType validationType) {
        return validationType.equals(type);
    }
}
