package com.albert.commerce.api.product.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;
import lombok.Builder;

public class ProductUpdatedEvent extends DomainEvent {

    private final DomainId productId;
    private final String productName;
    private final Money price;
    private final String brand;
    private final String category;
    private final String description;
    private final LocalDateTime updateTime;

    @Builder
    private ProductUpdatedEvent(
            DomainId productId,
            String productName,
            Money price,
            String brand,
            String category,
            String description,
            LocalDateTime updateTime
    ) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.updateTime = updateTime;
    }

    public DomainId getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Money getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
