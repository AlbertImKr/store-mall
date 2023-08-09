package com.albert.commerce.api.product.query.domain;

import com.albert.commerce.api.common.infra.persistence.Money;
import com.albert.commerce.api.common.infra.persistence.converters.MoneyConverter;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.domain.StoreId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product")
@Entity
public class ProductData {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;
    @AttributeOverride(name = "id", column = @Column(name = "store_id", nullable = false))
    @Embedded
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
}
