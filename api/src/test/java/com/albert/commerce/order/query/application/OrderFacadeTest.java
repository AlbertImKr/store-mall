package com.albert.commerce.order.query.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.application.command.order.OrderRequest;
import com.albert.commerce.application.command.order.OrderService;
import com.albert.commerce.application.command.product.ProductService;
import com.albert.commerce.application.command.product.dto.ProductRequest;
import com.albert.commerce.application.command.store.SellerStoreService;
import com.albert.commerce.application.command.store.dto.NewStoreRequest;
import com.albert.commerce.application.command.user.UserService;
import com.albert.commerce.application.query.order.OrderDetail;
import com.albert.commerce.application.query.order.OrderFacade;
import com.albert.commerce.application.query.order.OrderLineDetail;
import com.albert.commerce.application.query.product.ProductFacade;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.command.order.DeliveryStatus;
import com.albert.commerce.domain.command.order.OrderId;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.query.user.UserDao;
import com.albert.commerce.domain.query.user.UserData;
import com.albert.commerce.infra.command.product.persistence.imports.ProductJpaRepository;
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
            ProductId productId = productService.addProduct(userEmail,
                    new ProductRequest("testProductName", 10000, "test", "testBrand",
                            "testCategory"));
            productIdAndQuantity.put(productId.getId(), (long) i);
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
