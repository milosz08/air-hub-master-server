package pl.miloszgilga.ahms.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class InvalidDtoExceptionResDto extends ServerExceptionResDto {
    private final Map<String, String> errors;

    public InvalidDtoExceptionResDto(ServerExceptionResDto res, Map<String, String> errors) {
        super(res.getTimestamp(), res.getStatus(), res.getError(), res.getMethod());
        this.errors = errors;
    }
}
