package com.albert.commerce.domain.product;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.store.StoreId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ProductCreatedEvent extends DomainEvent {

    private final ProductId productId;
    private final StoreId storeId;
    private final String productName;
    private final Money price;
    private final String description;
    private final String brand;
    private final String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    public ProductCreatedEvent(ProductId productId, StoreId storeId, String productName, Money price,
            String description,
            String brand, String category, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.productId = productId;
        this.storeId = storeId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
