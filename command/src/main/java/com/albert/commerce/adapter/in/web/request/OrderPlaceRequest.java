package com.albert.commerce.adapter.in.web.request;

import java.util.Map;

public record OrderPlaceRequest(
        Map<String, Long> productsIdAndQuantity,
        String storeId
) {

}
