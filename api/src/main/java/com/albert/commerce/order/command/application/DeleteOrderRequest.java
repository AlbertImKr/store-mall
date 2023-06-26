package com.albert.commerce.order.command.application;

import com.albert.commerce.order.command.domain.OrderId;

public record DeleteOrderRequest(OrderId orderId,
                                 String description) {


}
