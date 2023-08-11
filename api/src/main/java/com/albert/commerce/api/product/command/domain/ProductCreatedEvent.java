package com.albert.commerce.api.product.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import lombok.Builder;

public class ProductCreatedEvent extends DomainEvent {

    private final DomainId productId;
    private final DomainId storeId;
    private final String productName;
    private final Money price;
    private final String description;
    private final String brand;
    private final String category;

    @Builder
    private ProductCreatedEvent(DomainId productId, DomainId storeId, String productName, Money price,
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

    public DomainId getProductId() {
        return productId;
    }

    public DomainId getStoreId() {
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
