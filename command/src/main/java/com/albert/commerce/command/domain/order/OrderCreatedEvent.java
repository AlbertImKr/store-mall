package com.albert.commerce.command.domain.order;

import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.common.units.DeliveryStatus;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class OrderCreatedEvent extends DomainEvent {

    private final OrderId orderId;
    private final UserId userId;
    private final StoreId storeId;
    private final List<OrderDetailRequest> orderDetailRequests;
    private final DeliveryStatus deliveryStatus;
    private final LocalDateTime createdTime;
    private final LocalDateTime updateTime;

    @Builder
    private OrderCreatedEvent(OrderId orderId, UserId userId, StoreId storeId,
            List<OrderDetailRequest> orderDetailRequests,
            DeliveryStatus deliveryStatus, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.orderDetailRequests = orderDetailRequests;
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

    public List<OrderDetailRequest> getOrderDetailRequests() {
        return orderDetailRequests;
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
