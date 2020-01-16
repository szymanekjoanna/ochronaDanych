package com.od.cryptography.common.controller;

import com.od.cryptography.common.errors.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class ErrorHandler {

    private final Logger log = Logger.getLogger(getClass().getName());

    @ExceptionHandler(ValidationError.class)
    public ResponseEntity<Object> validationErrorHandling(ValidationError error){
        log.warning(error.getMessage());
        return new ResponseEntity<Object>(error.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> exceptionHandling(Exception exception){
//        log.warning(exception.getMessage());
//        return new ResponseEntity<Object>("Błędne dane!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
}
