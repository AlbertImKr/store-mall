package com.albert.commerce.api.product.command.application.dto;

import com.albert.commerce.api.product.command.domain.Product;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.units.BusinessLinks;
import com.albert.commerce.common.infra.persistence.Money;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCreatedResponse that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getProductName(),
                that.getProductName()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(
                getDescription(), that.getDescription()) && Objects.equals(getBrand(), that.getBrand())
                && Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductId(), getProductName(), getPrice(), getDescription(),
                getBrand(),
                getCategory());
    }
}
