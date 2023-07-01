package com.albert.commerce.order.command.application;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderRepository;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(UserId userId, long amount, List<Product> products, StoreId storeId) {
        Order order = Order.builder()
                .orderId(orderRepository.nextId())
                .userId(userId)
                .storeId(storeId)
                .products(products)
                .amount(amount)
                .build();

        return orderRepository.save(order);
    }


    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}