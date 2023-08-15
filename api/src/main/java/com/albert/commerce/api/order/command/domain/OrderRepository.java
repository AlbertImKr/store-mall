package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.api.user.command.domain.UserId;
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
