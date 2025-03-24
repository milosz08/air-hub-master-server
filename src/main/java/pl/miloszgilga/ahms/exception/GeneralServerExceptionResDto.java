package pl.miloszgilga.ahms.exception;

import lombok.Getter;

@Getter
public class GeneralServerExceptionResDto extends ServerExceptionResDto {
    private final String message;

    public GeneralServerExceptionResDto(ServerExceptionResDto res, String message) {
        super(res.getTimestamp(), res.getStatus(), res.getError(), res.getMethod());
        this.message = message;
    }
}
