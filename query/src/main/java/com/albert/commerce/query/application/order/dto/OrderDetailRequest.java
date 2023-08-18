package com.albert.commerce.query.application.order.dto;

import com.albert.commerce.query.domain.product.ProductId;
import com.albert.commerce.query.infra.persistance.Money;
import lombok.Builder;

public class OrderDetailRequest {

    private final ProductId productId;
    private final Money price;
    private final long quantity;
    private final Money amount;

    @Builder
    private OrderDetailRequest(ProductId productId, Money price, long quantity, Money amount) {
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
