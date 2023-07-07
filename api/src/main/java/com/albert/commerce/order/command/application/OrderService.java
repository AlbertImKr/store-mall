package com.albert.commerce.order.command.application;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.OrderRepository;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void deleteOrder(DeleteOrderRequest deleteOrderRequest, UserId userId) {
        OrderId orderId = OrderId.from(deleteOrderRequest.orderId());
        checkOrder(orderId, userId);
        orderRepository.deleteById(orderId);
    }

    private void checkOrder(OrderId orderId, UserId userId) {
        if (!orderRepository.exist(orderId, userId)) {
            throw new OrderNotFoundException();
        }
    }

    @Transactional
    public Order createOrder(UserId userId, StoreId storeId, List<ProductId> productsId,
            long amount) {
        Order order = Order.builder()
                .userId(userId)
                .storeId(storeId)
                .productsId(productsId)
                .amount(amount)
                .build();
        return orderRepository.save(order);
    }


}
