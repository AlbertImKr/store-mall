package com.albert.commerce.domain.order;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.adapter.out.persistence.converters.MoneyConverter;
import com.albert.commerce.domain.product.ProductId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderDetail {

    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    @Embedded
    private ProductId productId;
    private String productName;
    private long quantity;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "ammount")
    private Money amount;
    private String productDescription;

    @Builder
    public OrderDetail(ProductId productId, String productName, long quantity, Money price, Money amount,
            String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.productDescription = productDescription;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getAmount() {
        return amount;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
