package com.albert.commerce.query.common.exception;

public class CommentNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.COMMENT_NOT_FOUND_ERROR;

    public CommentNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    protected ErrorMessage getErrorMessage() {
        return null;
    }
}
