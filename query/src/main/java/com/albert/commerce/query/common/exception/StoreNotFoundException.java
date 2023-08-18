package com.albert.commerce.query.common.exception;

public class StoreNotFoundException extends BusinessException {

    private static final com.albert.commerce.query.common.exception.ErrorMessage errorMessage = com.albert.commerce.query.common.exception.ErrorMessage.STORE_NOT_FOUND_ERROR;

    public StoreNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
