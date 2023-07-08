package com.albert.commerce.order.command.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.order.command.domain.DeliveryStatus;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.product.query.application.ProductFacade;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.query.application.UserFacade;
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
    UserFacade userFacade;
    @Autowired
    UserService userService;

    @Autowired
    ProductFacade productFacade;


    @Nested
    @DisplayName("Order를 필요한 테스트")
    class needSavedOrderTest {

        Order order;
        UserInfoResponse consumer;
        String userEmail = "test@email.com";

        @DisplayName("Order를 저장한다")
        @BeforeEach
        void save() {
            // given
            userService.createByEmail(userEmail);
            consumer = userFacade.findByEmail(userEmail);
            SellerStoreResponse store = sellerStoreService.createStore(
                    new NewStoreRequest("testStoreName",
                            "testOwnerName",
                            "testAddress",
                            "11111111111",
                            "testStore@email.com").toStore(consumer.getId()));
            List<ProductId> productsId = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ProductCreatedResponse productCreatedResponse = productService.addProduct(
                        new ProductRequest("testProductName", 10000, "test", "testBrand",
                                "testCategory").toProduct(store.getStoreId()));
                productsId.add(productCreatedResponse.getProductId());
            }

            // when
            order = orderService.placeOrder(consumer.getId(), store.getStoreId(), productsId,
                    productFacade.getAmount(productsId));

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
