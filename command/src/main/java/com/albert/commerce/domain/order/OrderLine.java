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
public class OrderLine {

    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    @Embedded
    private ProductId productId;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    @Column(name = "quantity")
    private long quantity;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;

    @Builder
    public OrderLine(ProductId productId, Money price, long quantity, Money amount) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Money getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public Money getAmount() {
        return amount;
    }
}
