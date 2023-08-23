package com.albert.commerce.exception.error;

public class MyStoreNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.STORE_NOT_FOUND_ERROR;

    public MyStoreNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
