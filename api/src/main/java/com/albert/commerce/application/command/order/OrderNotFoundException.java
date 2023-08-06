package com.albert.commerce.application.command.order;

import com.albert.commerce.common.exception.BusinessException;
import com.albert.commerce.common.exception.ErrorMessage;

public class OrderNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.ORDER_NOT_FOUND_ERROR;

    public OrderNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    protected ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
