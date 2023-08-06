package com.albert.commerce.product.application;

import com.albert.commerce.application.command.product.ProductService;
import com.albert.commerce.application.command.product.dto.ProductRequest;
import com.albert.commerce.application.command.store.SellerStoreService;
import com.albert.commerce.application.command.store.dto.NewStoreRequest;
import com.albert.commerce.application.command.user.UserService;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.query.user.UserDao;
import com.albert.commerce.domain.query.user.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
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
        sellerStoreService.createStore(user.getEmail(),
                new NewStoreRequest("storeName", "orderName", "address", "100-0001-0001",
                        "seller@email.com"));
        ProductRequest productRequest = new ProductRequest("testProductName",
                1000, "test", "testBrand", "test");

        // when
        ProductId productId = productService.addProduct(
                user.getEmail(), productRequest);

        // then
//        Assertions.assertAll(
//                () -> assertThat(productCreatedResponse.getProductName()).isEqualTo(
//                        productRequest.productName()),
//                () -> assertThat(productCreatedResponse.getDescription()).isEqualTo(
//                        productRequest.description()),
//                () -> assertThat(productCreatedResponse.getBrand()).isEqualTo(
//                        productRequest.brand()),
//                () -> assertThat(productCreatedResponse.getCategory()).isEqualTo(
//                        productRequest.category()),
//                () -> assertThat(productCreatedResponse.getPrice()).isEqualTo(
//                        new Money(productRequest.price()))
//        );
    }
}
