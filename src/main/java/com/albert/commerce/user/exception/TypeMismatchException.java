package com.albert.commerce.user.exception;

import org.springframework.security.core.AuthenticationException;

public class TypeMismatchException extends AuthenticationException {

    public TypeMismatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TypeMismatchException(String msg) {
        super(msg);
    }
}
