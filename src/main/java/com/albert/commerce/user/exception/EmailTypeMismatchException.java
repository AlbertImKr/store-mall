package com.albert.commerce.user.exception;

public class EmailTypeMismatchException extends TypeMismatchException {

    public EmailTypeMismatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmailTypeMismatchException(String msg) {
        super(msg);
    }
}
