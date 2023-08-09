package com.albert.commerce.api.store.command.application;

import com.albert.commerce.api.common.exception.BusinessException;
import com.albert.commerce.api.common.exception.ErrorMessage;

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
