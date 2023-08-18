package com.albert.commerce.query.application.product.dto;

import com.albert.commerce.query.domain.product.ProductId;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.infra.persistance.Money;

public record ProductCreatedEvent(
        ProductId productId,
        StoreId storeId,
        String productName,
        Money price,
        String description,
        String brand,
        String category
) {

}
