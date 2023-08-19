package com.albert.commerce.application.service.product.dto;

import com.albert.commerce.adapter.out.persistance.Money;
import com.albert.commerce.domain.product.ProductId;
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
