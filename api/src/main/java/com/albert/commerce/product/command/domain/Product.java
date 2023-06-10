package com.albert.commerce.product.command.domain;

import com.albert.commerce.common.jpa.MoneyConverter;
import com.albert.commerce.common.model.Money;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Product {

    @EmbeddedId
    private ProductId productId;

    private String productName;
    @Convert(converter = MoneyConverter.class)
    private Money price;
    private String description;
    private String brand;
    private String category;

    public Product() {
    }

    @Builder
    public Product(ProductId productId, String productName, Money price, String description,
            String brand, String category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

}
