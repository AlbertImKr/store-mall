package com.albert.commerce.common.exception;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(String message) {
        super(message);
    }

    protected abstract ErrorMessage getErrorMessage();
}
