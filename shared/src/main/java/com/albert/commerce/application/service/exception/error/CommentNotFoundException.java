package com.albert.commerce.application.service.exception.error;

public class CommentNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.COMMENT_NOT_FOUND_ERROR;

    public CommentNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return null;
    }
}
