package com.albert.commerce.command.domain.order;

import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.common.infra.persistence.Money;

public record OrderDetailRequest(
        ProductId productId,
        Money price,
        long quantity,
        Money amount
) {

}
