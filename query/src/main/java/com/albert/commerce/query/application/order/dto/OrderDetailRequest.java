package com.albert.commerce.query.application.order.dto;

import com.albert.commerce.query.domain.product.ProductId;
import com.albert.commerce.query.infra.persistance.Money;

public record OrderDetailRequest(
        ProductId productId,
        Money price,
        long quantity,
        Money amount
) {

}
