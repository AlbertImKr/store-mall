package com.albert.commerce.application.service.order.dto;

import com.albert.commerce.adapter.out.persistance.Money;
import com.albert.commerce.domain.product.ProductId;

public record OrderDetailRequest(
        ProductId productId,
        Money price,
        long quantity,
        Money amount
) {

}
