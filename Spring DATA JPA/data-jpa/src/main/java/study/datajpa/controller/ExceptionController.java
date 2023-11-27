//package study.datajpa.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.function.EntityResponse;
//import study.datajpa.exception.jpaException;
//import study.datajpa.response.ErrorResponse;
//
//@RestControllerAdvice
//public class ExceptionController {
//
//
//    @ExceptionHandler(jpaException.class)
//    public ResponseEntity<ErrorResponse> jpaExceptionHandler(jpaException e){
//        return ResponseEntity
//                .status(e.getStatusCode())
//                .body(ErrorResponse.builder()
//                        .errorCode(e.getStatusCode())
//                        .message(e.getMessage())
//                        .build());
//    }
//}
