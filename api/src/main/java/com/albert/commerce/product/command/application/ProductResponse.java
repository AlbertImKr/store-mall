package com.albert.commerce.product.command.application;

import com.albert.commerce.common.model.Money;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponse extends RepresentationModel<ProductResponse> {

    private ProductId productId;
    private String productName;
    private Money price;
    private String description;
    private String brand;
    private String category;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime updateTime;

    @Builder
    private ProductResponse(ProductId productId, String productName, Money price,
            String description,
            String brand, String category, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .createdTime(product.getCreatedTime())
                .updateTime(product.getUpdateTime())
                .productId(product.getProductId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .build();
    }
}
