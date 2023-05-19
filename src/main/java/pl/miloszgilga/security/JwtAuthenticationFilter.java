/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: JwtAuthenticationFilter.java
 * Last modified: 18/05/2023, 13:44
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.security;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.io.IOException;

import org.jmpsl.security.jwt.JwtService;
import org.jmpsl.security.jwt.JwtValidPayload;
import org.jmpsl.security.user.UserPrincipalAuthenticationToken;

import pl.miloszgilga.domain.blacklist_jwt.IBlacklistJwtRepository;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final IBlacklistJwtRepository blacklistJwtRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        final UserDetails userDetails = userDetailsService.loadUserByUsername(extractedLogin);
        if (blacklistJwtRepository.checkIfJwtIsOnBlacklist(token)) {
            chain.doFilter(req, res);
            return;
        }
        final var authenticationToken = new UserPrincipalAuthenticationToken(req, userDetails);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(req, res);
    }
}
