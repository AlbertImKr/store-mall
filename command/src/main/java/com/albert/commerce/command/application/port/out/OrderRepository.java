package com.albert.commerce.command.application.port.out;

import com.albert.commerce.command.domain.order.Order;
import com.albert.commerce.command.domain.order.OrderId;
import com.albert.commerce.command.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

    Order save(Order order);

    boolean exist(OrderId orderId, UserId userId);

    void deleteById(OrderId orderId);

    Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<Order> findAllByUserId(UserId userId, Pageable pageable);
}
