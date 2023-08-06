package com.albert.commerce.application.command.product.dto;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.domain.command.product.Product;
import com.albert.commerce.domain.command.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCreatedResponse extends RepresentationModel<ProductCreatedResponse> {

    private ProductId productId;
    private String productName;
    private Money price;
    private String description;
    private String brand;
    private String category;

    @Builder
    private ProductCreatedResponse(ProductId productId, String productName, Money price,
            String description,
            String brand, String category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public static ProductCreatedResponse from(Product product) {
        return ProductCreatedResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .build()
                .add(BusinessLinks.getProductSelfRel(product.getProductId()));
    }
}
