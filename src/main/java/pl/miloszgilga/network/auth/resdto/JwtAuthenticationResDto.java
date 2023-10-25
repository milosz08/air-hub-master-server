/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.network.auth.resdto;

import lombok.Builder;

@Builder
public record JwtAuthenticationResDto(
    String jwtToken,
    String refreshToken
) {
}
