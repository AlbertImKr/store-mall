package com.albert.commerce.query.application.order.dto;

import com.albert.commerce.query.domain.order.OrderId;
import java.time.LocalDateTime;

public record OrderCanceledEvent(OrderId orderId, LocalDateTime updatedTime) {

}
