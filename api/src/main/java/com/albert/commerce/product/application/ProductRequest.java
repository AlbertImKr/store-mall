package com.albert.commerce.product.application;

import com.albert.commerce.common.model.Money;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;

public record ProductRequest(String productName,
                             long price,
                             String description,
                             String brand,
                             String category) {


    public Product toProduct(StoreId storeId) {
        return Product.builder()
                .storeId(storeId)
                .productId(new ProductId())
                .productName(productName)
                .price(new Money(price))
                .description(description)
                .brand(brand)
                .category(category)
                .build();
    }
}
