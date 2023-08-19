package com.albert.commerce.application.service;

public class ProductUpdateCommand extends Command {

    private final String userEmail;
    private final String productId;
    private final String productName;
    private final int price;
    private final String description;
    private final String brand;
    private final String category;

    public ProductUpdateCommand(String userEmail, String productId, String productName, int price, String description,
            String brand,
            String category) {
        this.userEmail = userEmail;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }
}
