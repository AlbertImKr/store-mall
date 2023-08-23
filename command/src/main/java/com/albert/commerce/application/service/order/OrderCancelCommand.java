package com.albert.commerce.application.service.order;

import com.albert.commerce.application.service.Command;

public class OrderCancelCommand extends Command {

    private final String userEmail;
    private final String storeId;
    private final String description;

    public OrderCancelCommand(String userEmail, String storeId, String description) {
        this.userEmail = userEmail;
        this.storeId = storeId;
        this.description = description;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getDescription() {
        return description;
    }
}
