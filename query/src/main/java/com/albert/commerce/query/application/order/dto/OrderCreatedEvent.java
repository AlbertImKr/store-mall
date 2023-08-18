package com.albert.commerce.query.application.order.dto;

import com.albert.commerce.common.units.DeliveryStatus;
import com.albert.commerce.query.domain.order.OrderId;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserId;
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
