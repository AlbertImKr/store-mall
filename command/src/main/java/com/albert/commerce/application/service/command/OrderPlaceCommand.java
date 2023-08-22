package com.albert.commerce.application.service.command;

import com.albert.commerce.application.service.Command;
import java.util.Map;

public class OrderPlaceCommand extends Command {

    private final String userEmail;
    private final String storeId;
    private final Map<String, Long> productsIdAndQuantity;

    public OrderPlaceCommand(String userEmail, String storeId, Map<String, Long> productsIdAndQuantity) {
        this.userEmail = userEmail;
        this.storeId = storeId;
        this.productsIdAndQuantity = productsIdAndQuantity;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getStoreId() {
        return storeId;
    }

    public Map<String, Long> getProductsIdAndQuantity() {
        return productsIdAndQuantity;
    }
}
