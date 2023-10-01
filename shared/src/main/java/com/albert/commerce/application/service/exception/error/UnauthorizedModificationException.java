package com.albert.commerce.application.service.exception.error;

public class UnauthorizedModificationException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.UNAUTHORIZED_MODIFICATION_ERROR;

    public UnauthorizedModificationException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
