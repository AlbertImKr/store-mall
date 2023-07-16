package com.albert.commerce.order.query.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.DeliveryStatus;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.product.query.application.ProductFacade;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@SpringBootTest
class OrderFacadeTest {


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
    OrderFacade orderFacade;
    @Autowired
    ProductFacade productFacade;

    OrderId orderId;
    String userEmail = "test@email.com";
    UserData user;
    StoreId storeId;
    Map<String, Long> productIdAndQuantity = new HashMap<>();

    @DisplayName("Order를 저장한다")
    @BeforeEach
    void setOrder() {
        // User 저장
        userService.createByEmail(userEmail);
        user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        // store 생성
        storeId = sellerStoreService.createStore(user.getEmail(),
                new NewStoreRequest("testStoreName",
                        "testOwnerName",
                        "testAddress",
                        "11111111111",
                        "testStore@email.com"));
        for (int i = 0; i < 10; i++) {
            ProductCreatedResponse productCreatedResponse = productService.addProduct(userEmail,
                    new ProductRequest("testProductName", 10000, "test", "testBrand",
                            "testCategory"));
            productIdAndQuantity.put(productCreatedResponse.getProductId().getId(), (long) i);
        }
        orderId = orderService.placeOrder(userEmail, new OrderRequest(productIdAndQuantity
                , storeId.getId()
        ));
    }

    @DisplayName("주문 번호로 order 조회한다")
    @Test
    void findById() {
        OrderDetail orderData = orderFacade.findById(orderId, userEmail);
        assertThat(orderData.getOrderId()).isEqualTo(orderId);
        assertThat(orderData.getUserId()).isEqualTo(user.getUserId());
        assertThat(orderData.getDeliveryStatus()).isEqualTo(DeliveryStatus.PENDING);
        assertThat(orderData.getStoreId()).isEqualTo(storeId);
        assertThat(
                orderData.getOrderLineDetails().stream()
                        .map(OrderLineDetail::getProductId)
                        .map(ProductId::getId)
                        .toList()
        ).containsAll(
                new ArrayList<>(productIdAndQuantity.keySet())
        );
    }
}
