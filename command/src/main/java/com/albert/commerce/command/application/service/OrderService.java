package com.albert.commerce.command.application.service;

import com.albert.commerce.command.adapter.in.web.dto.DeleteOrderRequest;
import com.albert.commerce.command.adapter.in.web.dto.OrderRequest;
import com.albert.commerce.command.application.port.out.OrderRepository;
import com.albert.commerce.command.domain.order.Order;
import com.albert.commerce.command.domain.order.OrderId;
import com.albert.commerce.command.domain.order.OrderLine;
import com.albert.commerce.command.domain.product.Product;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.User;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.common.exception.OrderNotFoundException;
import com.albert.commerce.common.infra.persistence.Money;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
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
    public void cancelOrder(DeleteOrderRequest deleteOrderRequest, String userEmail) {
        UserId userId = userService.findIdByEmail(userEmail);
        OrderId orderId = OrderId.from(deleteOrderRequest.orderId());
        Order order = orderRepository.findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.cancel(LocalDateTime.now());
    }

    private static List<OrderLine> getOrderLines(Map<String, Long> productsIdAndQuantity, List<Product> products) {
        return products.stream()
                .map(toOrderLine(productsIdAndQuantity))
                .toList();
    }

    private static Function<Product, OrderLine> toOrderLine(Map<String, Long> productsIdAndQuantity) {
        return productData -> {
            Money price = productData.getPrice();
            Long quantity = productsIdAndQuantity.get(productData.getProductId().getValue());
            Money amount = price.multiply(quantity);
            return OrderLine.builder()
                    .productId(productData.getProductId())
                    .price(price)
                    .quantity(quantity)
                    .amount(amount)
                    .build();
        };
    }

    @Transactional
    public OrderId placeOrder(String userEmail, OrderRequest orderRequest) {
        User user = userService.getUserByEmail(userEmail);
        StoreId storeId = StoreId.from(orderRequest.storeId());
        storeService.checkId(storeId);
        Map<String, Long> productsIdAndQuantity = orderRequest.productsIdAndQuantity();
        List<Product> products = getProducts(productsIdAndQuantity);
        Order order = toOrder(user, storeId, productsIdAndQuantity, products);
        return orderRepository.save(order).getOrderId();
    }

    private List<Product> getProducts(Map<String, Long> productsIdAndQuantity) {
        return productsIdAndQuantity.keySet().stream()
                .map(productId -> productService.getProductById(ProductId.from(productId)))
                .toList();
    }

    private static Order toOrder(User user, StoreId storeId, Map<String, Long> productsIdAndQuantity,
            List<Product> products) {
        return Order.builder()
                .storeId(storeId)
                .userId(user.getUserId())
                .orderLines(getOrderLines(productsIdAndQuantity, products))
                .build();
    }
}
