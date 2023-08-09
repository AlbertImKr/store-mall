package com.albert.commerce.order.command.application;

import com.albert.commerce.api.order.command.application.OrderService;
import com.albert.commerce.api.order.command.domain.Order;
import com.albert.commerce.api.product.command.application.ProductService;
import com.albert.commerce.api.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.api.product.command.application.dto.ProductRequest;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.api.store.command.application.SellerStoreService;
import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.UserNotFoundException;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;
    @Autowired
    ProductJpaRepository productJpaRepository;

    @Autowired
    SellerStoreService sellerStoreService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    ProductFacade productFacade;


    @Nested
    @DisplayName("Order를 필요한 테스트")
    class needSavedOrderTest {

        Order order;
        UserData consumer;
        String userEmail = "test@email.com";

        @DisplayName("Order를 저장한다")
        @BeforeEach
        void save() {
            // given
            userService.createByEmail(userEmail);
            consumer = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
            StoreId storeId = sellerStoreService.createStore(
                    consumer.getEmail(),
                    new NewStoreRequest("testStoreName",
                            "testOwnerName",
                            "testAddress",
                            "11111111111",
                            "testStore@email.com"));
            List<ProductId> productsId = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ProductCreatedResponse productCreatedResponse = productService.addProduct(consumer.getEmail(),
                        new ProductRequest("testProductName", 10000, "test", "testBrand",
                                "testCategory"));
                productsId.add(productCreatedResponse.getProductId());
            }

//            // when
//            order = orderService.placeOrder(consumer.getUserId(), storeId.getStoreId(), productsId,
//                    productFacade.getAmount(productsId));
//
//            // then
//            assertThat(order.getAmount()).isEqualTo(20000);
//            assertThat(order.getDeliveryStatus()).isEqualTo(DeliveryStatus.PENDING);
//            assertThat(order.getProductsId()).isEqualTo(order.getProductsId());
//            assertThat(order.getCreatedTime()).isNotNull();
//            assertThat(order.getOrderId()).isNotNull();
//            assertThat(order.getUpdateTime()).isNotNull();
        }


    }


}
