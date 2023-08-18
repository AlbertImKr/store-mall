package com.albert.commerce.query.application.product.dto;

import com.albert.commerce.query.domain.product.ProductId;
import com.albert.commerce.query.infra.persistance.Money;
import java.time.LocalDateTime;

public record ProductUpdatedEvent(
        ProductId productId,
        String productName,
        Money price,
        String brand,
        String category,
        String description,
        LocalDateTime updateTime
) {

}
