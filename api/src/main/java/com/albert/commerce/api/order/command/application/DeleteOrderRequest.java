package com.albert.commerce.api.order.command.application;

public record DeleteOrderRequest(String orderId,
                                 String description) {


}
