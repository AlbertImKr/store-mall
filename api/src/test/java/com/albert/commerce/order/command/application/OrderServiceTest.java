package com.albert.commerce.order.command.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.order.command.domain.DeliveryStatus;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.query.domain.UserDao;
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


    @Nested
    @DisplayName("Order를 필요한 테스트")
    class needSavedOrderTest {

        Order order;
        String userEmail = "test@email.com";

        @DisplayName("Order를 저장한다")
        @BeforeEach
        void save() {
            // given
            userService.init(userEmail);
            SellerStoreResponse store = sellerStoreService.createStore(
                    new NewStoreRequest("testStoreName",
                            "testOwnerName",
                            "testAddress",
                            "11111111111",
                            "testStore@email.com"),
                    userEmail);
            List<String> productList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ProductCreatedResponse productCreatedResponse = productService.addProduct(
                        new ProductRequest("testProductName", new Money(10000), "test", "testBrand",
                                "testCategory"), store.getStoreId());
                productList.add(productCreatedResponse.getProductId().getId());
            }

            // when
            OrderRequest orderRequest = new OrderRequest(productList, store.getStoreId().getId());
            order = orderService.createOrder(
                    userEmail, orderRequest);

            // then
            assertThat(order.getAmount()).isEqualTo(20000);
            assertThat(order.getDeliveryStatus()).isEqualTo(DeliveryStatus.PENDING);
            assertThat(order.getProductsId()).isEqualTo(order.getProductsId());
            assertThat(order.getCreatedTime()).isNotNull();
            assertThat(order.getOrderId()).isNotNull();
            assertThat(order.getUpdateTime()).isNotNull();
        }


    }


}
