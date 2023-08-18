package com.albert.commerce.command.infra.persistence.imports;

import com.albert.commerce.command.domain.order.Order;
import com.albert.commerce.command.domain.order.OrderId;
import com.albert.commerce.command.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, OrderId> {

    boolean existsByOrderIdAndUserId(OrderId orderId, UserId userId);

    Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<Order> findAllByUserId(UserId userId, Pageable pageable);
}
