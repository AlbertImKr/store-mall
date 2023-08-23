package com.albert.commerce.adapter.in.web.request;

public record ProductCreateRequest(
        String productName,
        int price,
        String description,
        String brand,
        String category
) {

}
