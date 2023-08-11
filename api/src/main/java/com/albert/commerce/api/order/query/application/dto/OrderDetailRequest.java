package com.albert.commerce.api.order.query.application.dto;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import lombok.Builder;

public class OrderDetailRequest {

    private final DomainId productId;
    private final Money price;
    private final long quantity;
    private final Money amount;

    @Builder
    private OrderDetailRequest(DomainId productId, Money price, long quantity, Money amount) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }

    public DomainId getProductId() {
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
