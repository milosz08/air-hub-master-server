package pl.miloszgilga.ahms.exception.rest;

import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.exception.RestServiceServerException;
import org.springframework.http.HttpStatus;
import pl.miloszgilga.i18n.AppLocaleSet;

public class AccountException {
    @Slf4j
    public static class LoginAlreadyExistException extends RestServiceServerException {
        public LoginAlreadyExistException(String newLogin) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.LOGIN_ALREADY_EXIST_EXC);
            log.error("Attempt to change login '{}' on account, when it is already existing on another account.",
                newLogin);
        }
    }

    @Slf4j
    public static class EmailAddressAlreadyExistException extends RestServiceServerException {
        public EmailAddressAlreadyExistException(String newEmail) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.EMAIL_ADDRESS_ALREADY_EXIST_EXC);
            log.error("Attempt to change email '{}' on account, when it is already existing on another account.",
                newEmail);
        }
    }

    @Slf4j
    public static class PassedCredentialsNotValidException extends RestServiceServerException {
        public PassedCredentialsNotValidException(String login) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.USER_CREDENTIALS_INVALID_EXC);
            log.error("Attempt to pass invalid credentials (password) for re-define secure data for {}.", login);
        }
    }
}
