package com.albert.commerce.order.infra.persistence.imports;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.user.command.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaResponsibility extends JpaRepository<Order, OrderId> {

    boolean existsByOrderIdAndUserId(OrderId orderId, UserId userId);
}
