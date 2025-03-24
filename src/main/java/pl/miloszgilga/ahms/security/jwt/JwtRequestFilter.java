package pl.miloszgilga.ahms.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.miloszgilga.ahms.domain.blacklist_jwt.BlacklistJwtRepository;
import pl.miloszgilga.ahms.security.UserPrincipalAuthenticationToken;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final BlacklistJwtRepository blacklistJwtRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        final String token = jwtService.extractToken(request);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        final JwtValidPayload validationResult = jwtService.isValid(token);
        final Optional<Claims> claims = jwtService.extractClaims(token);
        if (!validationResult.isValid() || claims.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        final Claims extractedClaims = claims.get();
        final String extractedLogin = extractedClaims.get(JwtClaim.LOGIN.getName(), String.class);
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(extractedLogin);
        } catch (AuthenticationException ex) {
            filterChain.doFilter(request, response);
            return;
        }
        if (blacklistJwtRepository.checkIfJwtIsOnBlacklist(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        final var authenticationToken = new UserPrincipalAuthenticationToken(request, userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
