package com.albert.commerce.order.command.application;

import java.util.Map;

public record OrderRequest(
        Map<String, Long> productsIdAndQuantity,
        String storeId
) {

}
