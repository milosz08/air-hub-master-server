package pl.miloszgilga.ahms.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.miloszgilga.ahms.domain.user.UserEntity;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String TOKEN_TYPE = "Bearer ";
    private final JwtProperties jwtProperties;

    public String generateToken(UserEntity user) {
        final ClaimsBuilder claims = Jwts.claims();
        claims.add(JwtClaim.ID.getName(), user.getId());
        claims.add(JwtClaim.LOGIN.getName(), user.getLogin());
        claims.add(JwtClaim.ROLE.getName(), user.getRole().getRole());
        return Jwts.builder()
            .issuer(jwtProperties.getIssuer())
            .subject(user.getLogin())
            .claims(claims.build())
            .expiration(DateUtils.addSeconds(Date.from(Instant.now()), jwtProperties.getAccessExpiredSec()))
            .signWith(getSignatureKey())
            .compact();
    }

    public RefreshTokenPayloadDto generateRefreshToken() {
        final Date expiredAt = DateUtils.addSeconds(Date.from(Instant.now()), jwtProperties.getRefreshExpiredSec());
        final String token = UUID.randomUUID().toString();
        return new RefreshTokenPayloadDto(token, ZonedDateTime.ofInstant(expiredAt.toInstant(),
            ZonedDateTime.now().getZone()));
    }

    public String extractToken(HttpServletRequest req) {
        final String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_TYPE)) {
            return bearerToken.substring(TOKEN_TYPE.length());
        }
        return org.apache.commons.lang3.StringUtils.EMPTY;
    }

    public Optional<Claims> extractClaims(String token) {
        final ValidateJwtPayload tokenAfterValidation = insideValidateToken(token);
        final JwtValidPayload validPayload = isValid(token);
        if (validPayload.isValid() || validPayload.checkType(JwtValidationType.EXPIRED)) {
            return tokenAfterValidation.claims();
        }
        return Optional.empty();
    }

    public Claims unsafeExtractClaims(final String token) {
        return Jwts.parser()
            .verifyWith(getSignatureKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public JwtValidPayload isValid(final String token) {
        final ValidateJwtPayload tokenAfterValidation = insideValidateToken(token);
        if (!tokenAfterValidation.type().equals(JwtValidationType.GOOD)) {
            log.error("{} Token: {}", tokenAfterValidation.type().getMessage(), token);
            return new JwtValidPayload(false, tokenAfterValidation.type());
        }
        return new JwtValidPayload(true, tokenAfterValidation.type());
    }

    private ValidateJwtPayload insideValidateToken(final String token) {
        try {
            final Claims extractedClaims = unsafeExtractClaims(token);
            return new ValidateJwtPayload(JwtValidationType.GOOD, Optional.of(extractedClaims));
        } catch (MalformedJwtException ex) {
            return new ValidateJwtPayload(JwtValidationType.MALFORMED);
        } catch (ExpiredJwtException ex) {
            return new ValidateJwtPayload(JwtValidationType.EXPIRED, Optional.of(ex.getClaims()));
        } catch (JwtException ex) {
            return new ValidateJwtPayload(JwtValidationType.INVALID);
        } catch (IllegalArgumentException ex) {
            return new ValidateJwtPayload(JwtValidationType.OTHER);
        }
    }

    private SecretKey getSignatureKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
