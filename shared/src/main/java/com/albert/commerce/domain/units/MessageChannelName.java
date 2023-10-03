package com.albert.commerce.domain.units;

public class MessageChannelName {

    public static final String USER_UPLOAD_CHANNEL = "UserUploadCommand";
    public static final String STORE_REGISTER_CHANNEL = "StoreRegisterCommand";
    public static final String STORE_UPLOAD_CHANNEL = "StoreUploadCommand";
    public static final String STORE_DELETE_CHANNEL = "StoreDeleteCommand";
    public static final String PRODUCT_CREATE_CHANNEL = "ProductCreateCommand";
    public static final String PRODUCT_UPDATE_CHANNEL = "ProductUpdateCommand";
    public static final String PRODUCT_DELETE_CHANNEL = "ProductDeleteCommand";
    public static final String ORDER_PLACE_CHANNEL = "OrderPlaceCommand";
    public static final String ORDER_CANCEL_CHANNEL = "OrderCancelCommand";


    private MessageChannelName() {
        throw new IllegalStateException("Utility class");
    }
}
