package com.albert.commerce.command.adapter.in.web.dto;

import java.util.Map;

public record OrderPlaceRequest(
        Map<String, Long> productsIdAndQuantity,
        String storeId
) {

}
