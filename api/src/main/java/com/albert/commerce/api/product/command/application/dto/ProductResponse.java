package com.albert.commerce.api.product.command.application.dto;

import com.albert.commerce.api.product.command.domain.Product;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.units.BusinessLinks;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;


@Getter
public class ProductResponse extends RepresentationModel<ProductResponse> {

    private final ProductId productId;
    private final String productName;
    private final Money price;
    private final String description;
    private final String brand;
    private final String category;
    private final StoreId storeId;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime updateTime;

    @Builder
    private ProductResponse(ProductId productId, String productName, Money price,
            String description,
            String brand, String category, LocalDateTime createdTime, LocalDateTime updateTime,
            Links links, StoreId storeId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.storeId = storeId;
        add(links);
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .createdTime(product.getCreatedTime())
                .updateTime(product.getUpdateTime())
                .productId(product.getProductId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .storeId(product.getStoreId())
                .description(product.getDescription())
                .category(product.getCategory())
                .links(Links.of(BusinessLinks.getProductSelfRel(product.getProductId())))
                .price(product.getPrice())
                .build();
    }

    public static ProductResponse from(ProductData product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .brand(product.getBrand())
                .storeId(product.getStoreId())
                .description(product.getDescription())
                .category(product.getCategory())
                .links(Links.of(BusinessLinks.getProductSelfRel(product.getProductId())))
                .price(product.getPrice())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductResponse that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getProductName(),
                that.getProductName()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(
                getDescription(), that.getDescription()) && Objects.equals(getBrand(), that.getBrand())
                && Objects.equals(getCategory(), that.getCategory()) && Objects.equals(getStoreId(),
                that.getStoreId()) && Objects.equals(getCreatedTime(), that.getCreatedTime())
                && Objects.equals(getUpdateTime(), that.getUpdateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductId(), getProductName(), getPrice(), getDescription(),
                getBrand(),
                getCategory(), getStoreId(), getCreatedTime(), getUpdateTime());
    }
}
