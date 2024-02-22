package com.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {

    ;

    @ExceptionHandler(value = {ResourceNotFoundExceptionGeneric.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundExceptionGeneric ex) {

        ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> IllegalArgumentException(ResourceNotFoundExceptionGeneric ex) {

        ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
    }
}