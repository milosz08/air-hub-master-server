package pl.miloszgilga.ahms.security.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;

import java.io.IOException;

@Slf4j
@Component
public class AuthResolver extends ResponseResolverBase implements AuthenticationEntryPoint {
    public AuthResolver(ObjectMapper objectMapper, LocaleMessageService localeMessageService) {
        super(objectMapper, localeMessageService);
    }

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        log.error(authException.getMessage());
        sendResponse(request, response);
    }

    @Override
    protected HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
