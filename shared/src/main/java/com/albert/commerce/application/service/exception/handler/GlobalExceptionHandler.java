package com.albert.commerce.application.service.exception.handler;

import com.albert.commerce.application.service.exception.error.BusinessException;
import com.albert.commerce.application.service.exception.error.ErrorResponse;
import com.albert.commerce.application.service.exception.error.UnauthorizedModificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse storeAlreadyExistsExceptionHandler(
            BusinessException businessException
    ) {
        return new ErrorResponse(businessException.getErrorMessage());
    }

    @ExceptionHandler(UnauthorizedModificationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorizedModificationExceptionHandler(
            UnauthorizedModificationException unauthorizedModificationException,
            HttpServletRequest httpServletRequest) {
        Link selfRel = Link.of(httpServletRequest.getRequestURL().toString()).withSelfRel();
        ErrorResponse errorResponse = new ErrorResponse(
                unauthorizedModificationException.getErrorMessage());
        return errorResponse.add(
                selfRel
        );
    }
}
