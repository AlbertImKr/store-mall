package com.albert.commerce.order.infra.persistence.imports;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaResponsibility extends JpaRepository<Order, OrderId> {

}
