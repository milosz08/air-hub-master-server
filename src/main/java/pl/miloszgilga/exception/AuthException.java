/*
 * Copyright (c) 2023 by MILOSZ GILGA <https://miloszgilga.pl>
 * Silesian University of Technology
 */
package pl.miloszgilga.exception;

import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.exception.RestServiceAuthServerException;
import org.springframework.http.HttpStatus;
import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.i18n.AppLocaleSet;
import pl.miloszgilga.network.auth.reqdto.RefreshReqDto;

public class AuthException {
    @Slf4j
    public static class UserNotFoundException extends RestServiceAuthServerException {
        public UserNotFoundException() {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.USER_NOT_FOUND_EXC);
            log.error("Attempt to get non existing user.");
        }
    }

    @Slf4j
    public static class IncorrectJwtException extends RestServiceAuthServerException {
        public IncorrectJwtException(String jwt) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.INCORRECT_JWT_EXC);
            log.error("Attempt to pass invalid JWT. Passed JWT: {}", jwt);
        }
    }

    @Slf4j
    public static class RefreshTokenNotFoundException extends RestServiceAuthServerException {
        public RefreshTokenNotFoundException(String refreshToken) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.REFRESH_TOKEN_NOT_FOUND_EXC);
            log.error("Attempt to get non existing or expired refresh token. Token: {}", refreshToken);
        }
    }

    @Slf4j
    public static class OtaTokenNotFoundException extends RestServiceAuthServerException {
        public OtaTokenNotFoundException(String otaToken) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.OTA_TOKEN_NOT_FOUND_EXC);
            log.error("Attempt to get non existing or expierd ota token. Token: {}", otaToken);
        }
    }

    @Slf4j
    public static class UserAccountHasBeenAlreadyActivatedException extends RestServiceAuthServerException {
        public UserAccountHasBeenAlreadyActivatedException(UserEntity user) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.ACCOUNT_HAS_BEEN_ALREADY_ACTIVATED_EXC);
            log.error("Attempt to activate already activated account. Account data: {}", user);
        }
    }

    @Slf4j
    public static class JwtIsNotRelatedWithRefrehTokenException extends RestServiceAuthServerException {
        public JwtIsNotRelatedWithRefrehTokenException(RefreshReqDto reqDto) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.JWT_IS_NOT_RELATED_WITH_REFRESH_TOKEN_EXC);
            log.error("Attempt to refresh token with not related JWT and refresh token. Request: {}", reqDto);
        }
    }
}
