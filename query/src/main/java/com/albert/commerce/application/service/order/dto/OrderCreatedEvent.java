package com.albert.commerce.application.service.order.dto;

import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.units.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
        OrderId orderId,
        UserId userId,
        StoreId storeId,
        List<OrderDetailRequest> orderDetailRequests,
        DeliveryStatus deliveryStatus,
        LocalDateTime createdTime,
        LocalDateTime updateTime

) {

}
