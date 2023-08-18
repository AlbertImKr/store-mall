package com.albert.commerce.query.common.exception;

public class ProductNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.PRODUCT_NOT_FOUND_ERROR;

    public ProductNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
