package com.albert.commerce.application.command.product.dto;

public record ProductRequest(String productName,
                             int price,
                             String description,
                             String brand,
                             String category) {

}
