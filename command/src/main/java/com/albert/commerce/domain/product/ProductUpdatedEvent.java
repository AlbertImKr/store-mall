package com.albert.commerce.domain.product;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
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
}
