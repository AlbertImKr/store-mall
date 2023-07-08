package com.albert.commerce.order.command.application;

import java.util.List;

public record OrderRequest(
        List<String> productsId,
        String storeId
) {

}
