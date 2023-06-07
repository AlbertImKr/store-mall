package com.albert.authorizationserver.controller;

import com.albert.authorizationserver.exception.EmailAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> emailAlreadyExistsExceptionHandler(
            EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
