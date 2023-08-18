package com.albert.commerce.command.adapter.in.web.dto;

public record ProductRequest(String productName,
                             int price,
                             String description,
                             String brand,
                             String category) {

}
