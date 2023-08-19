package com.albert.commerce.application.service;

public class CommentCreateCommand extends Command {

    private final String userEmail;
    private final String productId;
    private final String storeId;
    private final String parentCommentId;
    private final String detail;

    public CommentCreateCommand(String userEmail, String productId, String storeId, String parentCommentId,
            String detail) {
        this.userEmail = userEmail;
        this.productId = productId;
        this.storeId = storeId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getProductId() {
        return productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public String getDetail() {
        return detail;
    }
}
