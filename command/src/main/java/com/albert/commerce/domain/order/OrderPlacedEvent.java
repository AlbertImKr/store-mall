package com.albert.commerce.domain.order;

import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.units.DeliveryStatus;
import com.albert.commerce.domain.user.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderPlacedEvent extends DomainEvent {

    private final OrderId orderId;
    private final UserId userId;
    private final StoreId storeId;
    private final List<OrderDetailRequest> orderDetailRequests;
    private final DeliveryStatus deliveryStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    public OrderPlacedEvent(OrderId orderId, UserId userId, StoreId storeId,
            List<OrderDetailRequest> orderDetailRequests,
            DeliveryStatus deliveryStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.orderDetailRequests = orderDetailRequests;
        this.deliveryStatus = deliveryStatus;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
