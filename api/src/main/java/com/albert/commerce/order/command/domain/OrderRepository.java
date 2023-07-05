package com.albert.commerce.order.command.domain;

import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(OrderId orderId, String userEmail);

    Order save(Order order);

    OrderId nextId();

    void delete(Order order);

    boolean exist(OrderId orderId, UserId id);

    void deleteById(OrderId orderId);
}
