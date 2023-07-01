package com.albert.commerce.order.query.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.product.command.application.ProductCreatedResponse;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.store.command.application.NewStoreRequest;
import com.albert.commerce.store.command.application.SellerStoreResponse;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.command.domain.User;
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
class OrderDetailServiceTest {


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
    OrderDetailService orderDetailService;


    Order order;
    String userEmail = "test@email.com";

    @DisplayName("Order를 저장한다")
    @BeforeEach
    void setOrder() {
        // User 저장
        userService.init(userEmail);
        User user = userDao.findUserProfileByEmail(userEmail);
        // store 생성
        SellerStoreResponse store = sellerStoreService.createStore(
                new NewStoreRequest("testStoreName",
                        "testOwnerName",
                        "testAddress",
                        "11111111111",
                        "testStore@email.com"),
                user.getId());
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductCreatedResponse productCreatedResponse = productService.addProduct(
                    new ProductRequest("testProductName", new Money(10000), "test", "testBrand",
                            "testCategory"), store.getStoreId());
            productList.add(productJpaRepository.findById(productCreatedResponse.getProductId())
                    .orElseThrow());
        }
        order = orderService.createOrder(
                user.getId(), 20000, productList, store.getStoreId());
    }

    @DisplayName("주문 번호로 order 조회한다")
    @Test
    void findById() {
        OrderDetail orderDetail = orderDetailService.findById(order.getOrderId(), userEmail);
        assertThat(orderDetail).usingRecursiveComparison().isEqualTo(order);
    }
}
