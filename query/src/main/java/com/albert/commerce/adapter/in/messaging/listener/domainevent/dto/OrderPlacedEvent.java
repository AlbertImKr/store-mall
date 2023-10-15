package com.albert.commerce.adapter.in.messaging.listener.domainevent.dto;

import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.units.DeliveryStatus;
import com.albert.commerce.domain.user.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.util.List;

public record OrderPlacedEvent(
        OrderId orderId,
        UserId userId,
        StoreId storeId,
        List<OrderDetailRequest> orderDetailRequests,
        DeliveryStatus deliveryStatus,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime createdTime,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime updatedTime
) {

}
