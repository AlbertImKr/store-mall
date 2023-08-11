package com.albert.commerce.order.query.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.api.order.command.application.OrderRequest;
import com.albert.commerce.api.order.command.application.OrderService;
import com.albert.commerce.api.order.command.domain.DeliveryStatus;
import com.albert.commerce.api.order.query.application.OrderDetail;
import com.albert.commerce.api.order.query.application.OrderFacade;
import com.albert.commerce.api.order.query.application.OrderLineDetail;
import com.albert.commerce.api.product.command.application.ProductService;
import com.albert.commerce.api.product.command.application.dto.ProductRequest;
import com.albert.commerce.api.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.exception.UserNotFoundException;
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
    StoreService storeService;

    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;

    @Autowired
    OrderFacade orderFacade;
    @Autowired
    ProductFacade productFacade;

    DomainId orderId;
    String userEmail = "test@email.com";
    UserData user;
    DomainId storeId;
    Map<String, Long> productIdAndQuantity = new HashMap<>();

    @DisplayName("Order를 저장한다")
    @BeforeEach
    void setOrder() {
        // User 저장
        userService.createByEmail(userEmail);
        user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        // store 생성
        storeId = storeService.createStore(user.getEmail(),
                new NewStoreRequest("testStoreName",
                        "testOwnerName",
                        "testAddress",
                        "11111111111",
                        "testStore@email.com"));
        for (int i = 0; i < 10; i++) {
            DomainId productId = productService.addProduct(userEmail,
                    new ProductRequest("testProductName", 10000, "test", "testBrand",
                            "testCategory"));
            productIdAndQuantity.put(productId.getValue(), (long) i);
        }
        orderId = orderService.placeOrder(userEmail, new OrderRequest(productIdAndQuantity
                , storeId.getValue()
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
                        .map(DomainId::getValue)
                        .toList()
        ).containsAll(
                new ArrayList<>(productIdAndQuantity.keySet())
        );
    }
}
