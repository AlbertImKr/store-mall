package com.albert.commerce.application.service.utils;

public class Success {

    private static final Success INSTANCE = new Success();

    private Success() {
    }

    public static Success getInstance() {
        return INSTANCE;
    }
}
