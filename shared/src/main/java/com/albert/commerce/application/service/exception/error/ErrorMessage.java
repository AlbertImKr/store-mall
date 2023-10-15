package com.albert.commerce.application.service.exception.error;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessage {
    STORE_ALREADY_EXISTS_ERROR("스토어 이미 존재합니다."),
    STORE_NOT_FOUND_ERROR("스토어가 존재 하지 않습니다."),
    UNAUTHORIZED_MODIFICATION_ERROR("수정 권한이 없습니다."),
    PRODUCT_NOT_FOUND_ERROR("존재하지 않는 상품입니다."),
    COMMENT_NOT_FOUND_ERROR("존재하지 않는 댓글입니다."),
    USER_NOT_FOUND_ERROR("존재 하지 않는 유저입니다."),
    ORDER_NOT_FOUND_ERROR("존재하지 않는 주문입니다."),
    UNAUTHORIZED_USER_ERROR("인증되지 않은 사용자입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }
}
