package com.albert.commerce.product.application;

import com.albert.commerce.api.product.command.application.ProductService;
import com.albert.commerce.api.product.command.application.dto.ProductRequest;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.user.UserNotFoundException;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
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
    StoreService storeService;

    @DisplayName("새로온 product를 추가한다")
    @Test
    void addProduct() {
        // given
        userService.createByEmail(TEST_EMAIL);
        UserData user = userDao.findByEmail(TEST_EMAIL)
                .orElseThrow(UserNotFoundException::new);
        storeService.createStore(user.getEmail(),
                new NewStoreRequest("storeName", "orderName", "address", "100-0001-0001",
                        "seller@email.com"));
        ProductRequest productRequest = new ProductRequest("testProductName",
                1000, "test", "testBrand", "test");

        // when
        ProductId productId = productService.addProduct(user.getEmail(), productRequest);

        // then
//        Assertions.assertAll(
//                () -> assertThat(productId.getProductName()).isEqualTo(
//                        productRequest.productName()),
//                () -> assertThat(productId.getDescription()).isEqualTo(
//                        productRequest.description()),
//                () -> assertThat(productId.getBrand()).isEqualTo(
//                        productRequest.brand()),
//                () -> assertThat(productId.getCategory()).isEqualTo(
//                        productRequest.category()),
//                () -> assertThat(productId.getPrice()).isEqualTo(
//                        new Money(productRequest.price()))
//        );
    }
}
