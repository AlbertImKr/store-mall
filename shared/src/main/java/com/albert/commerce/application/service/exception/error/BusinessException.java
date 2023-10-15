package com.albert.commerce.application.service.exception.error;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(String message) {
        super(message);
    }

    public abstract ErrorMessage getErrorMessage();
}
