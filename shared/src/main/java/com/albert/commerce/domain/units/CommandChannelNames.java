package com.albert.commerce.domain.units;

public class CommandChannelNames {

    public static final String USER_UPLOAD_CHANNEL = "UserUploadCommand";
    public static final String STORE_REGISTER_CHANNEL = "StoreRegisterCommand";
    public static final String STORE_UPLOAD_CHANNEL = "StoreUploadCommand";
    public static final String STORE_DELETE_CHANNEL = "StoreDeleteCommand";
    public static final String PRODUCT_CREATE_CHANNEL = "ProductCreateCommand";
    public static final String PRODUCT_UPDATE_CHANNEL = "ProductUpdateCommand";
    public static final String PRODUCT_DELETE_CHANNEL = "ProductDeleteCommand";
    public static final String ORDER_PLACE_CHANNEL = "OrderPlaceCommand";
    public static final String ORDER_CANCEL_CHANNEL = "OrderCancelCommand";
    public static final String COMMENT_POST_CHANNEL = "CommentPostCommand";
    public static final String COMMENT_UPDATE_CHANNEL = "CommentUpdateCommand";
    public static final String COMMENT_DELETE_CHANNEL = "CommentDeleteCommand";
    public static final String LOGIN_CHANNEL = "LoginCommand";

    private CommandChannelNames() {
        throw new IllegalStateException("Utility class");
    }
}
