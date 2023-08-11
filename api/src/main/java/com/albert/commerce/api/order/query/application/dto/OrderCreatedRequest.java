package com.albert.commerce.api.order.query.application.dto;

import com.albert.commerce.api.order.command.domain.DeliveryStatus;
import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public class OrderCreatedRequest {

    private final DomainId orderId;
    private final DomainId userId;
    private final DomainId storeId;
    private final List<OrderDetailRequest> orderDetailRequests;
    private final DeliveryStatus deliveryStatus;
    private final LocalDateTime createdTime;
    private final LocalDateTime updateTime;

    @Builder
    private OrderCreatedRequest(DomainId orderId, DomainId userId, DomainId storeId,
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

    public DomainId getOrderId() {
        return orderId;
    }

    public DomainId getUserId() {
        return userId;
    }

    public DomainId getStoreId() {
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
