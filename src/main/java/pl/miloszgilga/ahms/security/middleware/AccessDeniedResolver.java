package pl.miloszgilga.ahms.security.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;

import java.io.IOException;

@Slf4j
@Component
public class AccessDeniedResolver extends ResponseResolverBase implements AccessDeniedHandler {
    public AccessDeniedResolver(ObjectMapper objectMapper, LocaleMessageService localeMessageService) {
        super(objectMapper, localeMessageService);
    }

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException
    ) throws IOException {
        log.error(accessDeniedException.getMessage());
        sendResponse(request, response);
    }

    @Override
    protected HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}
