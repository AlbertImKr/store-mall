package com.albert.commerce.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    public static final String TEST_EMAIL = "test@email.com";
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @Autowired
    SellerStoreService sellerStoreService;

    @DisplayName("새로온 product를 추가한다")
    @Test
    void addProduct() {
        // given
        userService.createByEmail(TEST_EMAIL);
        UserData user = userDao.findByEmail(TEST_EMAIL)
                .orElseThrow(UserNotFoundException::new);
        SellerStoreResponse store = sellerStoreService.createStore(
                new NewStoreRequest("storeName", "orderName", "address", "100-0001-0001",
                        "seller@email.com").toStore(user.getUserId()));
        ProductRequest productRequest = new ProductRequest("testProductName",
                1000, "test", "testBrand", "test");

        // when
        ProductCreatedResponse productCreatedResponse = productService.addProduct(
                productRequest.toProduct(store.getStoreId()));

        // then
        Assertions.assertAll(
                () -> assertThat(productCreatedResponse.getProductName()).isEqualTo(
                        productRequest.productName()),
                () -> assertThat(productCreatedResponse.getDescription()).isEqualTo(
                        productRequest.description()),
                () -> assertThat(productCreatedResponse.getBrand()).isEqualTo(
                        productRequest.brand()),
                () -> assertThat(productCreatedResponse.getCategory()).isEqualTo(
                        productRequest.category()),
                () -> assertThat(productCreatedResponse.getPrice()).isEqualTo(
                        new Money(productRequest.price()))
        );
    }
}
