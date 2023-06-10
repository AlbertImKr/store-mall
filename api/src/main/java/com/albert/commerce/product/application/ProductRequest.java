package com.albert.commerce.product.application;

import com.albert.commerce.common.model.Money;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String productName;
    private long price;
    private String description;
    private String brand;
    private String category;

    public ProductRequest(String productName, long price, String description, String brand,
            String category) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }


    public Product toProduct() {
        return Product.builder()
                .productId(new ProductId())
                .productName(productName)
                .price(new Money(price))
                .description(description)
                .brand(brand)
                .category(category)
                .build();
    }
}
