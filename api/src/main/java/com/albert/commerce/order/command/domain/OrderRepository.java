package com.albert.commerce.order.command.domain;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(OrderId orderId, String userEmail);

    Order save(Order order);

    OrderId nextId();

    void delete(Order order);
}
