package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.common.exception.BusinessException;
import com.albert.commerce.common.exception.ErrorMessage;

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
