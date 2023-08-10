package com.albert.commerce.api.order.infra.persistence.imports;

import com.albert.commerce.api.order.command.domain.Order;
import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, OrderId> {

    boolean existsByOrderIdAndUserId(OrderId orderId, DomainId userId);

    Optional<Order> findByUserIdAndOrderId(DomainId userId, OrderId orderId);

    Page<Order> findAllByUserId(DomainId userId, Pageable pageable);
}
