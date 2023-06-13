package com.albert.commerce.common.exception;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    protected abstract ErrorMessage getErrorMessage();
}
