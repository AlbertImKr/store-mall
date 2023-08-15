package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;

public class OrderCancelEvent extends DomainEvent {

    private final OrderId orderId;
    private final LocalDateTime updatedTime;

    public OrderCancelEvent(OrderId orderId, LocalDateTime updatedTime) {
        this.orderId = orderId;
        this.updatedTime = updatedTime;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
