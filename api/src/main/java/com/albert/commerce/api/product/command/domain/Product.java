package com.albert.commerce.api.product.command.domain;

import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.infra.persistence.converters.MoneyConverter;
import com.albert.commerce.shared.messaging.domain.event.Events;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product")
@Entity
public class Product {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    private StoreId storeId;
    @Column(nullable = false)
    private String productName;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "price", nullable = false)
    private Money price;
    @Column(nullable = false)
    private String description;
    @Column
    private String brand;
    @Column
    private String category;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime createdTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updateTime;

    @Builder
    public Product(ProductId productId, StoreId storeId, String productName, Money price,
            String description, String brand, String category) {
        this.productId = productId;
        this.storeId = storeId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public void update(String productName, Money price, String brand, String category,
            String description, LocalDateTime updateTime) {
        this.productName = productName;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.updateTime = updateTime;
        ProductUpdatedEvent productUpdatedEvent = ProductUpdatedEvent.builder()
                .productId(productId)
                .productName(productName)
                .price(price)
                .brand(brand)
                .category(category)
                .description(description)
                .updateTime(updateTime)
                .build();
        Events.raise(productUpdatedEvent);
    }

    public void updateId(ProductId productId, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.productId = productId;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        ProductCreatedEvent productCreatedEvent = ProductCreatedEvent.builder()
                .productId(productId)
                .productName(productName)
                .description(description)
                .storeId(storeId)
                .brand(brand)
                .category(category)
                .price(price)
                .build();
        Events.raise(productCreatedEvent);
    }
}
