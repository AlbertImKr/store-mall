package com.albert.commerce.store.command.application;

import com.albert.commerce.common.ErrorMessage;
import com.albert.commerce.store.StoreException;

public class StoreAlreadyExistsError extends StoreException {

    private static final ErrorMessage errorMessage = ErrorMessage.STORE_ALREADY_EXISTS_ERROR;

    public StoreAlreadyExistsError() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
