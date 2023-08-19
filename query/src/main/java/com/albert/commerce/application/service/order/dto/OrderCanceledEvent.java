package com.albert.commerce.application.service.order.dto;

import com.albert.commerce.domain.order.OrderId;
import java.time.LocalDateTime;

public record OrderCanceledEvent(OrderId orderId, LocalDateTime updatedTime) {

}
