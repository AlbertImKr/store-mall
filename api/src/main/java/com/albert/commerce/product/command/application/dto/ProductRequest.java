package com.albert.commerce.product.command.application.dto;

public record ProductRequest(String productName,
                             int price,
                             String description,
                             String brand,
                             String category) {

}
