package com.albert.commerce.application.service.order;

import com.albert.commerce.application.port.out.OrderRepository;
import com.albert.commerce.application.service.exception.error.OrderNotFoundException;
import com.albert.commerce.application.service.product.ProductService;
import com.albert.commerce.application.service.store.StoreService;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
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
        var user = userService.getByEmail(orderPlaceCommand.getUserEmail());
        var storeId = StoreId.from(orderPlaceCommand.getStoreId());
        storeService.checkExist(storeId);
        var productsIdAndQuantity = orderPlaceCommand.getProductsIdAndQuantity();
        var products = getProducts(productsIdAndQuantity);
        var order = Order.from(getnewOrderId(), user, storeId, productsIdAndQuantity, products, LocalDateTime.now());
        return orderRepository.save(order)
                .getOrderId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "OrderCancelCommand")
    public void cancel(OrderCancelCommand orderCancelCommand) {
        var userId = userService.getUserIdByEmail(orderCancelCommand.getUserEmail());
        var orderId = OrderId.from(orderCancelCommand.getStoreId());
        var order = orderRepository.findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.cancel(LocalDateTime.now());
    }

    private OrderId getnewOrderId() {
        return orderRepository.nextId();
    }

    private List<Product> getProducts(Map<String, Long> productsIdAndQuantity) {
        return productsIdAndQuantity.keySet().stream()
                .map(productId -> productService.getProductById(ProductId.from(productId)))
                .toList();
    }
}
