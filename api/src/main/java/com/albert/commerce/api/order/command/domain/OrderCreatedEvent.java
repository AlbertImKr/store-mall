package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.units.DeliveryStatus;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class OrderCreatedEvent extends DomainEvent {

    private final OrderId orderId;
    private final UserId userId;
    private final StoreId storeId;
    private final List<OrderLine> orderLines;
    private final DeliveryStatus deliveryStatus;
    private final LocalDateTime createdTime;
    private final LocalDateTime updateTime;

    @Builder
    private OrderCreatedEvent(OrderId orderId, UserId userId, StoreId storeId, List<OrderLine> orderLines,
            DeliveryStatus deliveryStatus, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.orderLines = orderLines;
        this.deliveryStatus = deliveryStatus;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public UserId getUserId() {
        return userId;
    }

    public StoreId getStoreId() {
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
