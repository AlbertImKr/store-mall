package com.albert.commerce.command.application.product.dto;

public record ProductRequest(String productName,
                             int price,
                             String description,
                             String brand,
                             String category) {

}
