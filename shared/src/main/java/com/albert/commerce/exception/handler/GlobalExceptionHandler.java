package com.albert.commerce.exception.handler;

import com.albert.commerce.exception.error.ErrorResponse;
import com.albert.commerce.exception.error.MyStoreNotFoundException;
import com.albert.commerce.exception.error.ProductNotFoundException;
import com.albert.commerce.exception.error.StoreAlreadyExistsException;
import com.albert.commerce.exception.error.StoreNotFoundException;
import com.albert.commerce.exception.error.UnauthorizedModificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StoreAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse storeAlreadyExistsExceptionHandler(
            StoreAlreadyExistsException storeAlreadyExistsException,
            HttpServletRequest httpServletRequest) {
        Link selfRel = Link.of(httpServletRequest.getRequestURL().toString()).withSelfRel();
        ErrorResponse errorResponse = new ErrorResponse(
                storeAlreadyExistsException.getErrorMessage());
        return errorResponse.add(
                selfRel
        );
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse storeExceptionHandler(StoreNotFoundException storeNotFoundException,
            HttpServletRequest httpServletRequest) {
        Link selfRel = Link.of(httpServletRequest.getRequestURL().toString()).withSelfRel();
        ErrorResponse errorResponse = new ErrorResponse(storeNotFoundException.getErrorMessage());
        return errorResponse.add(
                selfRel
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse storeExceptionHandler(ProductNotFoundException productNotFoundException) {
        return new ErrorResponse(productNotFoundException.getErrorMessage());
    }


    @ExceptionHandler(MyStoreNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse storeExceptionHandler(MyStoreNotFoundException myStoreNotFoundException,
            HttpServletRequest httpServletRequest) {
        Link selfRel = Link.of(httpServletRequest.getRequestURL().toString()).withSelfRel();
        ErrorResponse errorResponse = new ErrorResponse(myStoreNotFoundException.getErrorMessage());
        return errorResponse.add(
                selfRel
        );
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
