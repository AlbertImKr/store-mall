package com.albert.commerce.store;

import com.albert.commerce.common.exception.AppException;
import com.albert.commerce.common.exception.ErrorMessage;

public abstract class StoreException extends AppException {


    public StoreException(String message) {
        super(message);
    }

    public abstract ErrorMessage getErrorMessage();
}
