package com.albert.commerce.store.command.application;

import com.albert.commerce.common.exception.ErrorMessage;
import com.albert.commerce.store.StoreException;

public class StoreAlreadyExistsException extends StoreException {

    private static final ErrorMessage errorMessage = ErrorMessage.STORE_ALREADY_EXISTS_ERROR;

    public StoreAlreadyExistsException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
