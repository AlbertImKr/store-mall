package com.albert.commerce.application.service.exception.error;

public class UserNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.USER_NOT_FOUND_ERROR;

    public UserNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    protected ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}