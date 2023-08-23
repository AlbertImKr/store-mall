package com.albert.commerce.domain.order;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.product.ProductId;

public record OrderDetailRequest(
        ProductId productId,
        Money price,
        long quantity,
        Money amount
) {

}
