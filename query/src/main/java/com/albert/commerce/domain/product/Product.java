package com.albert.commerce.domain.product;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.adapter.out.persistence.converters.MoneyConverter;
import com.albert.commerce.domain.store.StoreId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
@Entity
public class Product implements Serializable {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @Column(nullable = false)
    private String productName;
    @Convert(converter = MoneyConverter.class)
    @Column(nullable = false)
    private Money price;
    @Column(nullable = false)
    private String description;
    @Column
    private String brand;
    @Column
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime updatedTime;

    @Builder
    public Product(ProductId productId, StoreId storeId, String productName, Money price, String description,
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void update(String productName, String description, String brand, Money price, String category,
            LocalDateTime updateTime) {
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.updatedTime = updateTime;
    }
}
