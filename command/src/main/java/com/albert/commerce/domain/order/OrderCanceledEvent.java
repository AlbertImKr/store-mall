package com.albert.commerce.domain.order;

import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class OrderCanceledEvent extends DomainEvent {

    private final OrderId orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    public OrderCanceledEvent(OrderId orderId, LocalDateTime updatedTime) {
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
