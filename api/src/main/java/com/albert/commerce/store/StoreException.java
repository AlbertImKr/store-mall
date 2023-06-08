package com.albert.commerce.store;

import com.albert.commerce.common.AppException;
import com.albert.commerce.common.ErrorMessage;

public abstract class StoreException extends AppException {


    public StoreException(String message) {
        super(message);
    }

    public abstract ErrorMessage getErrorMessage();
}
