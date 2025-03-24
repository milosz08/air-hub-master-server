package pl.miloszgilga.ahms.network.auth.resdto;

import lombok.Builder;

@Builder
public record JwtAuthenticationResDto(
    String jwtToken,
    String refreshToken
) {
}
