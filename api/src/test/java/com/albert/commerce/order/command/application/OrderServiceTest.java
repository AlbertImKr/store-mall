package com.albert.commerce.order.command.application;

import com.albert.commerce.application.command.order.OrderService;
import com.albert.commerce.application.command.product.ProductService;
import com.albert.commerce.application.command.product.dto.ProductRequest;
import com.albert.commerce.application.command.store.SellerStoreService;
import com.albert.commerce.application.command.store.dto.NewStoreRequest;
import com.albert.commerce.application.command.user.UserService;
import com.albert.commerce.application.query.product.ProductFacade;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.command.order.Order;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.query.user.UserDao;
import com.albert.commerce.domain.query.user.UserData;
import com.albert.commerce.infra.command.product.persistence.imports.ProductJpaRepository;
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
                ProductId productId = productService.addProduct(consumer.getEmail(),
                        new ProductRequest("testProductName", 10000, "test", "testBrand",
                                "testCategory"));
                productsId.add(productId);
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
