package study.datajpa.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message;
    private int errorCode;

    @Builder
    private ErrorResponse(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
