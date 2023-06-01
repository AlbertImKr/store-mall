package com.albert.commerce.global;

import com.albert.commerce.user.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public RedirectView emailAlreadyExistsExceptionHandler(EmailAlreadyExistsException e) {
        RedirectView redirectView = new RedirectView("/users/new");
        redirectView.addStaticAttribute("error", e.getMessage());
        return redirectView;
    }
}
