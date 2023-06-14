package com.albert.commerce.common.exception;

import com.albert.commerce.store.command.application.StoreAlreadyExistsException;
import com.albert.commerce.store.command.application.StoreLinks;
import com.albert.commerce.store.ui.StoreNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({StoreNotFoundException.class, StoreNotFoundException.class,
            StoreAlreadyExistsException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse storeExceptionHandler(BusinessException businessException,
            HttpServletRequest httpServletRequest) {
        Link selfRel = Link.of(httpServletRequest.getRequestURL().toString()).withSelfRel();
        ErrorResponse errorResponse = new ErrorResponse(businessException.getErrorMessage());
        return errorResponse.add(
                selfRel,
                StoreLinks.MY_STORE,
                StoreLinks.ADD_STORE,
                StoreLinks.GET_STORE);
    }
}
