package com.albert.commerce.product.infra.persistence;

import com.albert.commerce.common.exception.BusinessException;
import com.albert.commerce.common.exception.ErrorMessage;

public class ProductNotFoundException extends BusinessException {

    private static final ErrorMessage errorMessage = ErrorMessage.PRODUCT_NOT_FOUND_ERROR;

    public ProductNotFoundException() {
        super(errorMessage.getMessage());
    }

    @Override
    protected ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
