package com.albert.commerce.common.handler;

import com.albert.commerce.common.exception.ErrorResponse;
import com.albert.commerce.common.exception.MyStoreNotFoundException;
import com.albert.commerce.common.exception.ProductNotFoundException;
import com.albert.commerce.common.exception.StoreAlreadyExistsException;
import com.albert.commerce.common.exception.StoreNotFoundException;
import com.albert.commerce.common.exception.UnauthorizedModificationException;
import com.albert.commerce.common.units.BusinessLinks;
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
                selfRel,
                BusinessLinks.CREATE_STORE
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
                selfRel,
                BusinessLinks.CREATE_STORE
        );
    }
}
