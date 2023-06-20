package com.albert.commerce.product;

import com.albert.commerce.common.exception.BusinessException;
import com.albert.commerce.common.exception.ErrorMessage;

public class UnauthorizedModificationException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.UNAUTHORIZED_MODIFICATION_ERROR;

    public UnauthorizedModificationException() {
        super(errorMessage.getMessage());
    }

    @Override
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
