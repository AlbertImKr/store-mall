package com.albert.commerce.api.order.infra.persistence.imports;

import com.albert.commerce.api.order.command.domain.Order;
import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, OrderId> {

    boolean existsByOrderIdAndUserId(OrderId orderId, UserId userId);

    Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<Order> findAllByUserId(UserId userId, Pageable pageable);
}
