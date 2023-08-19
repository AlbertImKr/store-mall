package com.albert.commerce.domain.order;

import com.albert.commerce.domain.user.UserId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Page<Order> findAllByUserId(UserId userId, Pageable pageable);

    List<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Order save(Order order);

    Optional<Order> findById(OrderId orderId);
}
