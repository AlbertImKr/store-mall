package com.albert.commerce.product.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.infra.persistence.converters.MoneyConverter;
import com.albert.commerce.store.command.domain.StoreId;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product extends BaseEntity {

    @EmbeddedId
    private ProductId productId;
    @Column(nullable = false)
    private StoreId storeId;
    @Column(nullable = false)
    private String productName;
    @Convert(converter = MoneyConverter.class)
    @Column(nullable = false)
    private Money price;
    @Column(nullable = false)
    private String description;
    @Column(nullable = true)
    private String brand;
    @Column(nullable = true)
    private String category;

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

    public Product update(String productName, Money price, String brand, String category,
            String description) {
        this.productName = productName;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.description = description;
        return this;
    }
}
