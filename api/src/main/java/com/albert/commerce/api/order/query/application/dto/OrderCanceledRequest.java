package com.albert.commerce.api.order.query.application.dto;

import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;

public record OrderCanceledRequest(DomainId orderId, LocalDateTime updatedTime) {

}
