package com.albert.commerce.order.query.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.command.domain.Product;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
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


    Order order;
    String userEmail = "test@email.com";

    @DisplayName("Order를 저장한다")
    @BeforeEach
    void setOrder() {
        // User 저장
        userService.init(userEmail);
        // store 생성
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
        OrderRequest orderRequest = new OrderRequest(productList, store.getStoreId().getId());
        order = orderService.createOrder(userEmail, orderRequest);
    }

    @DisplayName("주문 번호로 order 조회한다")
    @Test
    void findById() {
        OrderDetail orderDetail = orderFacade.findById(order.getOrderId(), userEmail);
        assertThat(orderDetail.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(orderDetail.getUserId()).isEqualTo(order.getUserId());
        assertThat(orderDetail.getDeliveryStatus()).isEqualTo(order.getDeliveryStatus());
        assertThat(orderDetail.getAmount()).isEqualTo(order.getAmount());
        assertThat(orderDetail.getStoreId()).isEqualTo(order.getStoreId());
        assertThat(orderDetail.getProducts().stream()
                .map(Product::getProductId)
                .toList()).containsAll(order.getProductsId());
    }
}
