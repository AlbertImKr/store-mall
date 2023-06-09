package com.albert.commerce.store.ui;

import com.albert.commerce.common.ErrorMessage;
import com.albert.commerce.store.StoreException;

public class StoreNotFoundException extends StoreException {

    private static final ErrorMessage errorMessage = ErrorMessage.STORE_NOT_FOUND_ERROR;

    public StoreNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
