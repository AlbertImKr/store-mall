package com.albert.commerce.exception;

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
