package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

    Order save(Order order);

    boolean exist(OrderId orderId, DomainId id);

    void deleteById(OrderId orderId);

    Optional<Order> findByUserIdAndOrderId(DomainId userId, OrderId orderId);

    Page<Order> findAllByUserId(DomainId userId, Pageable pageable);
}
