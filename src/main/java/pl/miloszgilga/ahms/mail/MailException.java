package pl.miloszgilga.ahms.mail;

import org.springframework.http.HttpStatus;
import pl.miloszgilga.ahms.exception.RestServiceServerException;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;

import java.util.Map;
import java.util.Set;

class MailException {
    static class UnableToSendEmailException extends RestServiceServerException {
        UnableToSendEmailException(Set<String> emailRecipents) {
            super(HttpStatus.SERVICE_UNAVAILABLE, AppLocaleSet.UNABLE_TO_SEND_EMAIL_EXC,
                Map.of("emailAddress", String.join(", ", emailRecipents)));
        }
    }
}
