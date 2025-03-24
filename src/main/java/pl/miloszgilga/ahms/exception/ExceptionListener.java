package pl.miloszgilga.ahms.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import pl.miloszgilga.ahms.i18n.AppLocaleSet;
import pl.miloszgilga.ahms.i18n.LocaleMessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
class ExceptionListener {
    private final LocaleMessageService localeMessageService;

    @ExceptionHandler(RestServiceServerException.class)
    ResponseEntity<GeneralServerExceptionResDto> restServiceServerException(
        RestServiceServerException ex,
        HttpServletRequest req
    ) {
        final String messageWithParams = localeMessageService.getMessage(ex.getLocaleEnumSet());
        final String message = extractVariablesFromMessage(messageWithParams, ex.getVariables());
        log.error("REST API exception. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
            ex.getStatus(), req), message), ex.getStatus());
    }

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<GeneralServerExceptionResDto> handleNotFoundError(Exception ex, HttpServletRequest req) {
        final String message = localeMessageService.getMessage(AppLocaleSet.NO_HANDLER_FOUND_EXC);
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(httpStatus, req),
            message), httpStatus);
    }

    @ExceptionHandler(RestServiceAuthServerException.class)
    public ResponseEntity<?> restServiceServerException(RestServiceAuthServerException ex, HttpServletRequest req) {
        final String messageWithParams = localeMessageService.getMessage(ex.getLocaleEnumSet());
        final String message = extractVariablesFromMessage(messageWithParams, ex.getVariables());
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        log.error("Unauthorized. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(httpStatus, req),
            message), httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> restServiceServerException(AuthenticationException ex, HttpServletRequest req) {
        log.error("Spring security authentication exception. Cause: {}", ex.getMessage());
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(httpStatus, req),
            ex.getMessage()), httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidArgumentException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if (fieldErrors.isEmpty()) {
            final ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
            return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(httpStatus,
                req), localeMessageService.getMessage(objectError.getDefaultMessage())), httpStatus);
        }
        final Map<String, String> errors = new HashMap<>();
        for (final FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), localeMessageService.getMessage(fieldError.getDefaultMessage()));
        }
        log.error("Bad request. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new InvalidDtoExceptionResDto(ServerExceptionResDto.generate(httpStatus, req),
            errors), httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> reqResParseException(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return internaServerError(AppLocaleSet.HTTP_MESSAGE_NOT_READABLE_EXC, ex.getMessage(), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerException(Exception ex, HttpServletRequest req) {
        return internaServerError(AppLocaleSet.INTERNAL_SERVER_ERROR_EXC, ex.getMessage(), req);
    }

    private ResponseEntity<GeneralServerExceptionResDto> internaServerError(
        AppLocaleSet appLocaleSet,
        String message,
        HttpServletRequest req
    ) {
        final String i18nMessage = localeMessageService.getMessage(appLocaleSet);
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("Internal server error. Cause: {}", message);
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(httpStatus, req),
            i18nMessage), httpStatus);
    }

    private String extractVariablesFromMessage(String message, Map<String, Object> variables) {
        for (final Map.Entry<String, Object> variable : variables.entrySet()) {
            message = message.replace("{{" + variable.getKey() + "}}", String.valueOf(variable.getValue()));
        }
        return message;
    }
}
