package com.albert.commerce.product.command.application;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.store.command.domain.StoreId;

public record ProductRequest(String productName,
                             Money price,
                             String description,
                             String brand,
                             String category) {


    public Product toProduct(StoreId storeId) {
        return Product.builder()
                .storeId(storeId)
                .productName(productName)
                .price(price)
                .description(description)
                .brand(brand)
                .category(category)
                .build();
    }
}
