package com.albert.commerce.common.handler;

import com.albert.commerce.common.exception.ErrorResponse;
import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.ProductNotFoundException;
import com.albert.commerce.product.UnauthorizedModificationException;
import com.albert.commerce.store.MyStoreNotFoundException;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.application.StoreAlreadyExistsException;
import com.albert.commerce.store.ui.SellerStoreController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
                selfRel,
                BusinessLinks.MY_STORE
        );
    }

    private static String getMyStoreRequestPath() {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                SellerStoreController.class).getMyStore(null)).toUri().getPath();
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse storeExceptionHandler(StoreNotFoundException storeNotFoundException,
            HttpServletRequest httpServletRequest) {
        Link selfRel = Link.of(httpServletRequest.getRequestURL().toString()).withSelfRel();
        ErrorResponse errorResponse = new ErrorResponse(storeNotFoundException.getErrorMessage());
        if (httpServletRequest.getRequestURI().equals(getMyStoreRequestPath())) {
            return errorResponse.add(selfRel, BusinessLinks.CREATE_STORE);
        }
        return errorResponse.add(
                selfRel,
                BusinessLinks.GET_STORE
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
                BusinessLinks.MY_STORE,
                BusinessLinks.CREATE_STORE
        );
    }
}
