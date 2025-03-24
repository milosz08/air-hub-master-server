package pl.miloszgilga.ahms.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import pl.miloszgilga.ahms.i18n.LocaleEnumSet;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RestServiceAuthServerException extends AuthenticationException {
    private final HttpStatus status;
    private final LocaleEnumSet localeEnumSet;
    private final Map<String, Object> variables = new HashMap<>();

    public RestServiceAuthServerException(HttpStatus status, LocaleEnumSet localeEnumSet) {
        super(localeEnumSet.getHolder());
        this.status = status;
        this.localeEnumSet = localeEnumSet;
    }
}
