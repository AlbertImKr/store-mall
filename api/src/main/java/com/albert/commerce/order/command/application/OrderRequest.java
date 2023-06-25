package com.albert.commerce.order.command.application;

import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import java.util.List;

public record OrderRequest(
        List<ProductId> productsId,
        StoreId storeId,
        long amount
) {

}
