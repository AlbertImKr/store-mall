package com.albert.commerce.api.store;

import com.albert.commerce.api.common.exception.BusinessException;
import com.albert.commerce.api.common.exception.ErrorMessage;

public class StoreNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.STORE_NOT_FOUND_ERROR;

    public StoreNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
