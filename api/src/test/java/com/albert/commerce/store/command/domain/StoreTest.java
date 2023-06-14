package com.albert.commerce.store.command.domain;

import com.albert.commerce.product.command.domain.ProductId;
import org.junit.jupiter.api.BeforeEach;

class StoreTest {

    Store store;
    ProductId productId;

    @BeforeEach
    void setStore() {
        store = new Store();
        productId = new ProductId();
    }

}
