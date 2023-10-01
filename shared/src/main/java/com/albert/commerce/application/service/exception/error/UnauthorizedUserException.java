package com.albert.commerce.application.service.exception.error;

public class UnauthorizedUserException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.UNAUTHORIZED_USER_ERROR;

    public UnauthorizedUserException() {
        super(errorMessage.getMessage());
    }

    @Override
    protected ErrorMessage getErrorMessage() {
        return null;
    }
}
