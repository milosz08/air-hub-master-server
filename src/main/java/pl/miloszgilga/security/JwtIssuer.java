/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.jmpsl.security.jwt.JwtService;
import org.springframework.stereotype.Service;
import pl.miloszgilga.domain.user.UserEntity;

@Service
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtService jwtService;

    public String generateTokenForUser(UserEntity user) {
        final Claims customClaims = Jwts.claims();
        customClaims.put(JwtClaim.ID.getName(), user.getId());
        customClaims.put(JwtClaim.LOGIN.getName(), user.getLogin());
        customClaims.put(JwtClaim.ROLE.getName(), user.getRole().getRole());
        return jwtService.generateToken(user.getLogin(), customClaims);
    }
}
