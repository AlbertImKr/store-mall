package com.albert.commerce.domain.product;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ProductUpdatedEvent extends DomainEvent {

    private final ProductId productId;
    private final String productName;
    private final Money price;
    private final String brand;
    private final String category;
    private final String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    public ProductUpdatedEvent(
            ProductId productId,
            String productName,
            Money price,
            String brand,
            String category,
            String description,
            LocalDateTime updatedTime
    ) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.updatedTime = updatedTime;
    }

    public ProductId getProductId() {
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

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
