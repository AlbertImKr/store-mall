package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    OrderId nextId();
}
