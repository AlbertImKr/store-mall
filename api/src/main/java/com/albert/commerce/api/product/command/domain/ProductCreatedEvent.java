package com.albert.commerce.api.product.command.domain;

import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import lombok.Builder;

public class ProductCreatedEvent extends DomainEvent {

    private final ProductId productId;
    private final StoreId storeId;
    private final String productName;
    private final Money price;
    private final String description;
    private final String brand;
    private final String category;

    @Builder
    private ProductCreatedEvent(ProductId productId, StoreId storeId, String productName, Money price,
            String description,
            String brand, String category) {
        this.productId = productId;
        this.storeId = storeId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public ProductId getProductId() {
        return productId;
    }

    public StoreId getStoreId() {
        return storeId;
    }

    public String getProductName() {
        return productName;
    }

    public Money getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }
}
