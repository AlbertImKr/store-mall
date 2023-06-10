package com.albert.commerce.store.command.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.product.domain.ProductId;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreTest {

    Store store;
    ProductId productId;

    @BeforeEach
    void setStore() {
        store = new Store();
        productId = new ProductId();
    }

    @DisplayName("상품을 아이디를 추가한다")
    @Test
    void addProduct() {
        Set<ProductId> products = store.getProductIds();
        assertThat(products.contains(productId)).isFalse();

        store.addProductId(productId);
        assertThat(products.contains(productId)).isTrue();
    }

    @DisplayName("상품 아이디를 삭제한다")
    @Test
    void removeProduct() {
        Set<ProductId> products = store.getProductIds();
        store.addProductId(productId);
        assertThat(products.contains(productId)).isTrue();

        store.removeProductId(productId);
        assertThat(store.getProductIds().contains(productId)).isFalse();
    }
}
