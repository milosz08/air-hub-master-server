package pl.miloszgilga.ahms.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ServerExceptionResDto {
    private final String timestamp;
    private final int status;
    private final String error;
    private final String method;

    public static ServerExceptionResDto generate(HttpStatus status, HttpServletRequest req) {
        return ServerExceptionResDto.builder()
            .method(req.getMethod())
            .status(status.value())
            .error(status.name())
            .timestamp(ZonedDateTime.now(ZoneOffset.UTC).toString())
            .build();
    }
}
