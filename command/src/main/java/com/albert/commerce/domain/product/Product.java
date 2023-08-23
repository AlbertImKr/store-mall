package com.albert.commerce.domain.product;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.adapter.out.persistence.converters.MoneyConverter;
import com.albert.commerce.application.service.product.ProductCreateCommand;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.store.Store;
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
        Events.raise(toProductCreatedEvent());
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

    private ProductUpdatedEvent toProductUpdatedEvent() {
        return new ProductUpdatedEvent(
                productId,
                productName,
                price,
                brand,
                category,
                description,
                updatedTime
        );
    }

    private ProductCreatedEvent toProductCreatedEvent() {
        return new ProductCreatedEvent(
                productId,
                storeId,
                productName,
                price,
                description,
                brand,
                category,
                createdTime,
                updatedTime
        );
    }

    public static Product from(ProductId productId, ProductCreateCommand productCreateCommand, Store store,
            LocalDateTime createdTime) {
        return Product.builder()
                .productId(productId)
                .storeId(store.getStoreId())
                .productName(productCreateCommand.getProductName())
                .price(new Money(productCreateCommand.getPrice()))
                .description(productCreateCommand.getDescription())
                .brand(productCreateCommand.getBrand())
                .category(productCreateCommand.getCategory())
                .createdTime(createdTime)
                .build();
    }
}
