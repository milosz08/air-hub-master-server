package pl.miloszgilga.ahms.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.miloszgilga.ahms.i18n.LocaleEnumSet;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RestServiceServerException extends RuntimeException {
    private final HttpStatus status;
    private final LocaleEnumSet localeEnumSet;
    private Map<String, Object> variables = new HashMap<>();

    public RestServiceServerException(HttpStatus status, LocaleEnumSet localeEnumSet) {
        super(localeEnumSet.getHolder());
        this.status = status;
        this.localeEnumSet = localeEnumSet;
    }

    public RestServiceServerException(HttpStatus status, LocaleEnumSet localeEnumSet, Map<String, Object> variables) {
        this(status, localeEnumSet);
        this.variables = variables;
    }
}
