/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmpsl.security.jwt.JwtService;
import org.jmpsl.security.jwt.JwtValidPayload;
import org.jmpsl.security.user.UserPrincipalAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.miloszgilga.domain.blacklist_jwt.BlacklistJwtRepository;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final BlacklistJwtRepository blacklistJwtRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws ServletException, IOException {
        final String token = jwtService.extractToken(req);
        if (!StringUtils.hasText(token)) {
            chain.doFilter(req, res);
            return;
        }
        final JwtValidPayload validationResult = jwtService.isValid(token);
        final Optional<Claims> claims = jwtService.extractClaims(token);
        if (!validationResult.isValid() || claims.isEmpty()) {
            chain.doFilter(req, res);
            return;
        }
        final Claims extractedClaims = claims.get();
        final String extractedLogin = extractedClaims.get(JwtClaim.LOGIN.getName(), String.class);
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(extractedLogin);
        } catch (AuthenticationException ex) {
            chain.doFilter(req, res);
            return;
        }
        if (blacklistJwtRepository.checkIfJwtIsOnBlacklist(token)) {
            chain.doFilter(req, res);
            return;
        }
        final var authenticationToken = new UserPrincipalAuthenticationToken(req, userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(req, res);
    }
}
