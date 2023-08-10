package com.albert.commerce.api.order.infra.persistence.imports;

import com.albert.commerce.api.order.command.domain.Order;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, DomainId> {

    boolean existsByOrderIdAndUserId(DomainId orderId, DomainId userId);

    Optional<Order> findByUserIdAndOrderId(DomainId userId, DomainId orderId);

    Page<Order> findAllByUserId(DomainId userId, Pageable pageable);
}
