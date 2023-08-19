package com.albert.commerce.application.service.product.dto;

import com.albert.commerce.adapter.out.persistance.Money;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;

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
