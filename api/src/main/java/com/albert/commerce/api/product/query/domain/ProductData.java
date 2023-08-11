package com.albert.commerce.api.product.query.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.infra.persistence.converters.MoneyConverter;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_query")
@Entity
public class ProductData {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private DomainId productId;
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private DomainId storeId;
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

    @Builder
    public ProductData(DomainId productId, DomainId storeId, String productName, Money price, String description,
            String brand, String category) {
        this.productId = productId;
        this.storeId = storeId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public DomainId getProductId() {
        return productId;
    }

    public DomainId getStoreId() {
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
}
