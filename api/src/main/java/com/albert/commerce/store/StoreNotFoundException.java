package com.albert.commerce.store;

import com.albert.commerce.common.exception.BusinessException;
import com.albert.commerce.common.exception.ErrorMessage;

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
