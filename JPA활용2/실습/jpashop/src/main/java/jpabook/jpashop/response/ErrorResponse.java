package jpabook.jpashop.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private String errorCode;
    private String message;

    @Builder
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
