package com.albert.commerce.application.service.exception.error;

public class OrderNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.ORDER_NOT_FOUND_ERROR;

    public OrderNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
