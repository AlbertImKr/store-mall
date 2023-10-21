package com.albert.commerce.application.service.order;


import static com.albert.commerce.domain.units.CommandChannelNames.ORDER_CANCEL_CHANNEL;
import static com.albert.commerce.domain.units.CommandChannelNames.ORDER_PLACE_CHANNEL;

import com.albert.commerce.application.port.out.OrderRepository;
import com.albert.commerce.application.service.exception.error.OrderNotFoundException;
import com.albert.commerce.application.service.product.ProductService;
import com.albert.commerce.application.service.store.StoreService;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
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
    @ServiceActivator(inputChannel = ORDER_PLACE_CHANNEL)
    public String place(OrderPlaceCommand orderPlaceCommand) {
        var user = userService.getByEmail(orderPlaceCommand.getUserEmail());
        var storeId = StoreId.from(orderPlaceCommand.getStoreId());
        storeService.checkExist(storeId);
        var order = createOrder(orderPlaceCommand, user, storeId);
        return orderRepository.save(order)
                .getOrderId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = ORDER_CANCEL_CHANNEL)
    public Success cancel(OrderCancelCommand orderCancelCommand) {
        var userId = userService.getUserIdByEmail(orderCancelCommand.getUserEmail());
        var orderId = OrderId.from(orderCancelCommand.getOrderId());
        var order = getOrderByUserIdAndOrderId(userId, orderId);
        order.cancel(LocalDateTime.now());
        return Success.getInstance();
    }

    public Order getOrderByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderRepository.findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    private Order createOrder(OrderPlaceCommand orderPlaceCommand, User user, StoreId storeId) {
        var productsIdAndQuantity = orderPlaceCommand.getProductsIdAndQuantity();
        var products = getProducts(productsIdAndQuantity);
        var orderId = getnewOrderId();
        var userId = user.getUserId();
        var createdTime = LocalDateTime.now();
        return Order.from(orderId, storeId, productsIdAndQuantity, products, createdTime, userId);
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
