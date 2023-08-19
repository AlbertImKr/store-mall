package com.albert.commerce.application.service;

import com.albert.commerce.application.port.out.OrderRepository;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.OrderNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final StoreService storeService;
    private final ProductService productService;

    @Transactional
    @ServiceActivator(inputChannel = "OrderPlaceCommand")
    public String place(OrderPlaceCommand orderPlaceCommand) {
        User user = userService.getUserByEmail(orderPlaceCommand.getUserEmail());
        StoreId storeId = StoreId.from(orderPlaceCommand.getStoreId());
        storeService.checkId(storeId);
        Map<String, Long> productsIdAndQuantity = orderPlaceCommand.getProductsIdAndQuantity();
        List<Product> products = getProducts(productsIdAndQuantity);
        Order order = Order.from(user, storeId, productsIdAndQuantity, products);
        return orderRepository.save(order)
                .getOrderId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "OrderCancelCommand")
    public void cancel(OrderCancelCommand orderCancelCommand) {
        UserId userId = userService.getUserIdByEmail(orderCancelCommand.getUserEmail());
        OrderId orderId = OrderId.from(orderCancelCommand.getStoreId());
        Order order = orderRepository.findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.cancel(LocalDateTime.now());
    }

    private List<Product> getProducts(Map<String, Long> productsIdAndQuantity) {
        return productsIdAndQuantity.keySet().stream()
                .map(productId -> productService.getProductById(ProductId.from(productId)))
                .toList();
    }
}
