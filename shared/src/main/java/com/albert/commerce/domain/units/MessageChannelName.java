package com.albert.commerce.domain.units;

public class MessageChannelName {

    public static final String USER_UPLOAD_CHANNEL = "UserUploadCommand";

    private MessageChannelName() {
        throw new IllegalStateException("Utility class");
    }
}
