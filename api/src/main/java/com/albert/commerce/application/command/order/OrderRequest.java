package com.albert.commerce.application.command.order;

import java.util.Map;

public record OrderRequest(
        Map<String, Long> productsIdAndQuantity,
        String storeId
) {

}
