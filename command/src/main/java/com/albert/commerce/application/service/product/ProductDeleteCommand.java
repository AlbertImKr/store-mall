package com.albert.commerce.application.service.product;

import com.albert.commerce.application.service.Command;

public class ProductDeleteCommand extends Command {

    private final String userEmail;
    private final String productId;

    public ProductDeleteCommand(String userEmail, String productId) {
        this.userEmail = userEmail;
        this.productId = productId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getProductId() {
        return productId;
    }
}
