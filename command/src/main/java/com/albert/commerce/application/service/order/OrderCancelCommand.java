package com.albert.commerce.application.service.order;

import com.albert.commerce.application.service.Command;

public class OrderCancelCommand extends Command {

    private final String userEmail;
    private final String orderId;
    private final String description;

    public OrderCancelCommand(String userEmail, String orderId, String description) {
        this.userEmail = userEmail;
        this.orderId = orderId;
        this.description = description;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDescription() {
        return description;
    }
}
