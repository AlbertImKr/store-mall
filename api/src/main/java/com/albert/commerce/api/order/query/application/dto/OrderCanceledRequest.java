package com.albert.commerce.api.order.query.application.dto;

import com.albert.commerce.api.order.command.domain.OrderId;
import java.time.LocalDateTime;

public record OrderCanceledRequest(OrderId orderId, LocalDateTime updatedTime) {

}
