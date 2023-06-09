package com.albert.commerce.common;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.albert.commerce.store.StoreException;
import com.albert.commerce.store.ui.StoreController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StoreException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse storeExceptionHandler(StoreException storeException) {
        ErrorResponse errorResponse = new ErrorResponse(storeException.getErrorMessage());
        return errorResponse.add(
                linkTo(methodOn(StoreController.class).getMyStore(null))
                        .withRel("my-store"),
                linkTo(methodOn(StoreController.class).addStore(null, null, null))
                        .withRel("add-store"),
                linkTo(methodOn(StoreController.class).getStore(null))
                        .withRel("other-store"));
    }
}
