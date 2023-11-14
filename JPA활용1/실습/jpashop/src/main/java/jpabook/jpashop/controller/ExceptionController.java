package jpabook.jpashop.controller;

import jpabook.jpashop.exception.ShopException;
import jpabook.jpashop.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ShopException.class)
    public ResponseEntity<ErrorResponse> shopExceptionHandler(ShopException e){
        int statusCode = e.getStatusCode();
        String message = e.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(String.valueOf(statusCode))
                .message(message)
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(errorResponse);
    }
}
