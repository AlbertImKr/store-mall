package com.albert.commerce.command.application.order;

import java.util.Map;

public record OrderRequest(
        Map<String, Long> productsIdAndQuantity,
        String storeId
) {

}
