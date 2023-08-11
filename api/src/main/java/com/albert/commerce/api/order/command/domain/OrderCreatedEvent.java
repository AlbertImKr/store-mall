package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class OrderCreatedEvent extends DomainEvent {

    private final DomainId orderId;
    private final DomainId userId;
    private final DomainId storeId;
    private final List<OrderLine> orderLines;
    private final DeliveryStatus deliveryStatus;
    private final LocalDateTime createdTime;
    private final LocalDateTime updateTime;

    @Builder
    private OrderCreatedEvent(DomainId orderId, DomainId userId, DomainId storeId, List<OrderLine> orderLines,
            DeliveryStatus deliveryStatus, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.orderLines = orderLines;
        this.deliveryStatus = deliveryStatus;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }

    public DomainId getOrderId() {
        return orderId;
    }

    public DomainId getUserId() {
        return userId;
    }

    public DomainId getStoreId() {
        return storeId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
