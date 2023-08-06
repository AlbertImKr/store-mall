package com.albert.commerce.domain.command.order;

import com.albert.commerce.domain.command.user.UserId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

    Order save(Order order);

    boolean exist(OrderId orderId, UserId id);

    void deleteById(OrderId orderId);

    Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<Order> findAllByUserId(UserId userId, Pageable pageable);
}
