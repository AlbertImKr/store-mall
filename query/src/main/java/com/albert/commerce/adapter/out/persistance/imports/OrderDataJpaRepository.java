package com.albert.commerce.adapter.out.persistance.imports;

import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.user.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataJpaRepository extends JpaRepository<Order, OrderId> {

    List<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<Order> findByUserId(UserId userId, Pageable pageable);
}