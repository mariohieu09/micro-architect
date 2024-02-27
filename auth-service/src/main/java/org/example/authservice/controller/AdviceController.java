package org.example.authservice.controller;

import org.example.authservice.dto.ErrorMessage;
import org.example.authservice.exception.BadCredentialException;
import org.example.authservice.exception.UserNameExistedException;
import org.example.authservice.exception.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AdviceController {


    @ExceptionHandler(UserNameExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleUsernameExistedException(UserNameExistedException ex){
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .timeStamp(ex.getTimeStamp())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleUsernameNotFoundException(UsernameNotFoundException ex){
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .timeStamp(ex.getTimeStamp())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadCredentialException(BadCredentialException ex){
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .timeStamp(ex.getTimeStamp())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }


}
