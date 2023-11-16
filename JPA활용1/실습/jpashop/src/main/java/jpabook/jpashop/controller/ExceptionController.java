package jpabook.jpashop.controller;

import jpabook.jpashop.exception.ShopException;
import jpabook.jpashop.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(ShopException.class)
//    public ResponseEntity<ErrorResponse> shopExceptionHandler(ShopException e){
//        int statusCode = e.getStatusCode();
//        String message = e.getMessage();
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .errorCode(String.valueOf(statusCode))
//                .message(message)
//                .build();
//
//        return ResponseEntity
//                .status(statusCode)
//                .body(errorResponse);
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String shopExceptionHandler(MethodArgumentNotValidException e, Model model){
        String statusCode = e.getStatusCode().toString();
        String message = e.getFieldErrors().get(0).getDefaultMessage();
        model.addAttribute("errorCode",statusCode);
        model.addAttribute("errorMessage",message);

        return "error";
    }
}
