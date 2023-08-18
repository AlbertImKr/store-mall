package com.albert.commerce.query.common.exception;

public class OrderNotFoundException extends BusinessException {

    private static final com.albert.commerce.query.common.exception.ErrorMessage errorMessage = com.albert.commerce.query.common.exception.ErrorMessage.ORDER_NOT_FOUND_ERROR;

    public OrderNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    protected ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
