package com.albert.commerce.api.order.command.application;

import com.albert.commerce.api.order.command.domain.Order;
import com.albert.commerce.api.order.command.domain.OrderLine;
import com.albert.commerce.api.order.command.domain.OrderRepository;
import com.albert.commerce.api.product.command.application.ProductService;
import com.albert.commerce.api.product.command.domain.Product;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.command.domain.User;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.exception.OrderNotFoundException;
import com.albert.commerce.common.exception.StoreNotFoundException;
import com.albert.commerce.common.infra.persistence.Money;
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

    private static Order toOrder(User user, DomainId storeId, Map<String, Long> productsIdAndQuantity,
            List<Product> products) {
        return Order.builder()
                .storeId(storeId)
                .userId(user.getUserId())
                .orderLines(getOrderLines(productsIdAndQuantity, products))
                .build();
    }

    private void checkOrder(DomainId orderId, DomainId userId) {
        if (!orderRepository.exist(orderId, userId)) {
            throw new OrderNotFoundException();
        }
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
    public void cancelOrder(DeleteOrderRequest deleteOrderRequest, String userEmail) {
        DomainId userId = userService.findIdByEmail(userEmail);
        DomainId orderId = DomainId.from(deleteOrderRequest.orderId());
        checkOrder(orderId, userId);
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public DomainId placeOrder(String userEmail, OrderRequest orderRequest) {
        User user = userService.getUserByEmail(userEmail);
        DomainId storeId = DomainId.from(orderRequest.storeId());
        if (!storeService.exists(storeId)) {
            throw new StoreNotFoundException();
        }
        Map<String, Long> productsIdAndQuantity = orderRequest.productsIdAndQuantity();
        List<Product> products = getProducts(productsIdAndQuantity);
        Order order = toOrder(user, storeId, productsIdAndQuantity, products);
        return orderRepository.save(order).getOrderId();
    }

    private List<Product> getProducts(Map<String, Long> productsIdAndQuantity) {
        return productsIdAndQuantity.keySet().stream()
                .map(productId -> productService.getProductById(DomainId.from(productId)))
                .toList();
    }
}
