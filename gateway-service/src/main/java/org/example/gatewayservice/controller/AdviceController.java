package org.example.gatewayservice.controller;

import org.example.gatewayservice.entity.ErrorMessage;
import org.example.gatewayservice.exception.ForbiddenException;
import org.example.gatewayservice.exception.UnAuthorizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AdviceController {



    @ExceptionHandler(UnAuthorizeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessage> handleUnAuthorizeException(UnAuthorizeException ex){
        return new ResponseEntity<>(ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(ex.getTimestamp())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> handleForbiddenException(ForbiddenException ex){
        return new ResponseEntity<>(ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(ex.getTimestamp())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .build(), HttpStatus.FORBIDDEN);
    }
}
