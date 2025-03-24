package pl.miloszgilga.ahms.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocaleMessageService {
    private final MessageSource messageSource;

    public String getMessage(LocaleEnumSet placeholder, Map<String, Object> attributes) {
        try {
            String resourceText = messageSource
                .getMessage(placeholder.getHolder(), null, LocaleContextHolder.getLocale());
            if (resourceText.isBlank()) {
                return placeholder.getHolder();
            }
            for (final Map.Entry<String, Object> param : attributes.entrySet()) {
                resourceText = resourceText.replace("{{" + param.getKey() + "}}", String.valueOf(param.getValue()));
            }
            return resourceText;
        } catch (NoSuchMessageException ex) {
            return placeholder.getHolder();

        }
    }

    public String getMessage(LocaleEnumSet placeholder) {
        return getMessage(placeholder, Map.of());
    }

    public String getMessage(String placeholder) {
        try {
            final String resourceText = messageSource.getMessage(placeholder, null, LocaleContextHolder.getLocale());
            if (resourceText.isBlank()) {
                return placeholder;
            }
            return resourceText;
        } catch (NoSuchMessageException ex) {
            return placeholder;
        }
    }
}
