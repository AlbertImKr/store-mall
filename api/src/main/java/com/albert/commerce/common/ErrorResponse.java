package com.albert.commerce.common;

public class ErrorResponse {

    private ErrorMessage errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
