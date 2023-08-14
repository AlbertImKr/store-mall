package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;

public class OrderCancelEvent extends DomainEvent {

    private final DomainId orderId;
    private final LocalDateTime updatedTime;

    public OrderCancelEvent(DomainId orderId, LocalDateTime updatedTime) {
        this.orderId = orderId;
        this.updatedTime = updatedTime;
    }

    public DomainId getOrderId() {
        return orderId;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
