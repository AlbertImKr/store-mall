package com.albert.commerce.adapter.out.persistence.imports;

import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.user.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, OrderId> {

    List<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<Order> findByUserId(UserId userId, Pageable pageable);
}
