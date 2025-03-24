package pl.miloszgilga.ahms.security.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import pl.miloszgilga.ahms.exception.GeneralServerExceptionResDto;
import pl.miloszgilga.ahms.exception.ServerExceptionResDto;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
abstract class ResponseResolverBase {
    private final ObjectMapper objectMapper;
    private final LocaleMessageService localeMessageService;

    protected void sendResponse(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String i18nMessage = localeMessageService.getMessage(AppLocaleSet.SECURITY_AUTHENTICATION_EXC);
        final GeneralServerExceptionResDto resDto = new GeneralServerExceptionResDto(
            ServerExceptionResDto.generate(status(), request), i18nMessage
        );
        final String jsonResponse = objectMapper.writeValueAsString(resDto);
        response.setStatus(status().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(jsonResponse);
    }

    protected abstract HttpStatus status();
}
