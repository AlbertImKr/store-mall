package com.albert.commerce.user.exception;

public class PasswordTypeMismatchException extends TypeMismatchException {

    public PasswordTypeMismatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PasswordTypeMismatchException(String msg) {
        super(msg);
    }
}
