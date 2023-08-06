package com.albert.commerce.application.command.store;

import com.albert.commerce.common.exception.BusinessException;
import com.albert.commerce.common.exception.ErrorMessage;

public class StoreAlreadyExistsException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.STORE_ALREADY_EXISTS_ERROR;

    public StoreAlreadyExistsException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
