package com.albert.commerce.domain.product;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.adapter.out.persistence.converters.MoneyConverter;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.store.StoreId;
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
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder
    private Product(ProductId productId, StoreId storeId, String productName, Money price,
            String description, String brand, String category, LocalDateTime createdTime, LocalDateTime updatedTime) {
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

    public void upload(String productName, Money price, String brand, String category,
            String description, LocalDateTime updateTime) {
        this.productName = productName;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.updatedTime = updateTime;
        Events.raise(toProductUpdatedEvent());
    }

    public ProductId getProductId() {
        return productId;
    }

    public Money getPrice() {
        return price;
    }

    public void updateId(ProductId productId, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.productId = productId;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        Events.raise(toProductCreatedEvent());
    }

    private ProductUpdatedEvent toProductUpdatedEvent() {
        return ProductUpdatedEvent.builder()
                .productId(this.productId)
                .productName(productName)
                .price(price)
                .brand(brand)
                .category(category)
                .description(description)
                .updatedTime(updatedTime)
                .build();
    }

    private ProductCreatedEvent toProductCreatedEvent() {
        return ProductCreatedEvent.builder()
                .productId(productId)
                .productName(productName)
                .description(description)
                .storeId(storeId)
                .brand(brand)
                .category(category)
                .price(price)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();
    }
}
