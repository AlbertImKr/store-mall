package com.albert.commerce.common.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessage {
    STORE_ALREADY_EXISTS_ERROR("스토어 이미 존재합니다."),
    STORE_NOT_FOUND_ERROR("스토어가 존재 하지 않습니다");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }
}
