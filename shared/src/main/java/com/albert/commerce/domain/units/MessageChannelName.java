package com.albert.commerce.domain.units;

public class MessageChannelName {

    public static final String USER_UPLOAD_CHANNEL = "UserUploadCommand";
    public static final String STORE_REGISTER_CHANNEL = "StoreRegisterCommand";
    public static final String STORE_UPLOAD_CHANNEL = "StoreUploadCommand";
    public static final String STORE_DELETE_CHANNEL = "StoreDeleteCommand";


    private MessageChannelName() {
        throw new IllegalStateException("Utility class");
    }
}
