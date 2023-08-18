package com.albert.commerce.shared.messaging.application;

public class ProductCreateCommand extends Command {

    private final String userEmail;
    private final String productName;
    private final int price;
    private final String description;
    private final String brand;
    private final String category;

    public ProductCreateCommand(String userEmail, String productName, int price, String description, String brand,
            String category) {
        this.userEmail = userEmail;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public String getUserEmail() {
        return userEmail;
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
