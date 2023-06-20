package com.albert.commerce.product.command.application;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProdutcCreatedResponse extends RepresentationModel<ProdutcCreatedResponse> {

    private ProductId productId;
    private String productName;
    private Money price;
    private String description;
    private String brand;
    private String category;

    @Builder
    private ProdutcCreatedResponse(ProductId productId, String productName, Money price,
            String description,
            String brand, String category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public static ProdutcCreatedResponse from(Product product) {
        return ProdutcCreatedResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .build();
    }
}
