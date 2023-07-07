package com.albert.commerce.order.command.domain;

import com.albert.commerce.user.command.domain.UserId;

public interface OrderRepository {

    Order save(Order order);

    boolean exist(OrderId orderId, UserId id);

    void deleteById(OrderId orderId);
}
