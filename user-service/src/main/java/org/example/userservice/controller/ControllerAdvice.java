package org.example.userservice.controller;

import org.example.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex){
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .timeStamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
