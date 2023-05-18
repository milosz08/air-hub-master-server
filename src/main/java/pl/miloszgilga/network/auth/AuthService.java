/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: AuthService.java
 * Last modified: 17/05/2023, 16:05
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

package pl.miloszgilga.network.auth;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Date;
import java.time.Instant;
import java.time.ZonedDateTime;

import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.security.OtaTokenService;
import org.jmpsl.security.user.AuthUser;

import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.dto.SimpleMessageResDto;
import pl.miloszgilga.security.JwtIssuer;
import pl.miloszgilga.security.GrantedUserRole;

import pl.miloszgilga.network.AbstractRestService;
import pl.miloszgilga.network.auth.resdto.LoginResDto;
import pl.miloszgilga.network.auth.reqdto.LoginReqDto;
import pl.miloszgilga.network.auth.reqdto.RegisterReqDto;
import pl.miloszgilga.network.auth.reqdto.ActivateAccountReqDto;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.user.IUserRepository;
import pl.miloszgilga.domain.ota_token.OtaTokenType;
import pl.miloszgilga.domain.ota_token.OtaTokenEntity;
import pl.miloszgilga.domain.ota_token.IOtaTokenRepository;
import pl.miloszgilga.domain.refresh_token.RefreshTokenEntity;
import pl.miloszgilga.domain.refresh_token.IRefreshTokenRepository;

import static pl.miloszgilga.exception.AuthException.UserNotFoundException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService extends AbstractRestService implements IAuthService {

    private final JwtIssuer jwtIssuer;
    private final OtaTokenService otaTokenService;
    private final PasswordEncoder passwordEncoder;
    private final LocaleMessageService messageService;
    private final AuthenticationManager authenticationManager;

    private final IUserRepository userRepository;
    private final IOtaTokenRepository otaTokenRepository;
    private final IRefreshTokenRepository refreshTokenRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public LoginResDto login(LoginReqDto reqDto) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(reqDto.getLoginOrEmail(), reqDto.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var principal = (AuthUser) authentication.getPrincipal();

        final UserEntity userEntity = userRepository.findUserByLoginOrEmail(principal.getUsername())
            .orElseThrow(UserNotFoundException::new);

        final String token = jwtIssuer.generateTokenForUser(userEntity);
        final RefreshTokenEntity refreshToken = refreshTokenRepository.findRefreshTokenByUserLogin(userEntity.getLogin())
            .orElseGet(() -> {
                final var generatedRefreshToken = jwtIssuer.generateRefreshTokenForUser(userEntity.getLogin());
                final RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                    .token(generatedRefreshToken.token())
                    .expiredAt(generatedRefreshToken.expiredDate())
                    .build();
                refreshTokenRepository.save(refreshTokenEntity);
                log.info("Successfully created refresh token for '{}' account", principal.getUsername());
                return refreshTokenEntity;
            });
        if (refreshToken.getExpiredAt().isBefore(ZonedDateTime.now())) {
            final var generatedRefreshToken = jwtIssuer.generateRefreshTokenForUser(principal.getUsername());
            refreshToken.setToken(generatedRefreshToken.token());
            refreshToken.setExpiredAt(generatedRefreshToken.expiredDate());
            refreshTokenRepository.save(refreshToken);
            log.info("Successfully re-validated expired refresh token for '{}' account", principal.getUsername());
        }
        log.info("Successfully login on '{}' account", principal.getUsername());
        return LoginResDto.builder()
            .jwtToken(token)
            .refreshToken(refreshToken.getToken())
            .build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public SimpleMessageResDto register(RegisterReqDto reqDto) {
        final var generatedRefreshToken = jwtIssuer.generateRefreshTokenForUser(reqDto.getLogin());
        final RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
            .token(generatedRefreshToken.token())
            .expiredAt(generatedRefreshToken.expiredDate())
            .build();

        String token;
        final Date expiredAt = DateUtils.addMinutes(Date.from(Instant.now()), 10);
        do {
            token = otaTokenService.generateToken();
        } while (otaTokenRepository.checkIfTokenAlreadyExist(token));

        final OtaTokenEntity otaTokenEntity = OtaTokenEntity.builder()
            .token(token)
            .type(OtaTokenType.ACTIVATE_ACCOUNT)
            .expiredAt(ZonedDateTime.ofInstant(expiredAt.toInstant(), ZonedDateTime.now().getZone()))
            .build();

        final UserEntity userEntity = UserEntity.builder()
            .firstName(StringUtils.capitalize(reqDto.getFirstName()))
            .lastName(StringUtils.capitalize(reqDto.getLastName()))
            .login(reqDto.getLogin())
            .emailAddress(reqDto.getEmailAddress())
            .password(passwordEncoder.encode(reqDto.getPassword()))
            .role(GrantedUserRole.STANDARD)
            .build();

        // sending email messages

        userEntity.persistRefreshToken(refreshTokenEntity);
        userEntity.persistOtaToken(otaTokenEntity);
        userRepository.save(userEntity);

        log.info("Successfully registered new user. User data: {}", userEntity);
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.SUCCESSFULL_REGISTERED_RES,
            Map.of("email", reqDto.getEmailAddress())));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public SimpleMessageResDto activateAccount(ActivateAccountReqDto reqDto) {
        // code here
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.SUCCESSFULL_ACTIVATED_ACCOUNT_RES));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public SimpleMessageResDto logout() {
        // code here
        return new SimpleMessageResDto(messageService.getMessage(AppLocaleSet.SUCCESSFULL_LOGOUT_RES));
    }
}
