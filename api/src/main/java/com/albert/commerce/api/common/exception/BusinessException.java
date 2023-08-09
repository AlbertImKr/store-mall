package com.albert.commerce.api.common.exception;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    protected abstract ErrorMessage getErrorMessage();
}
