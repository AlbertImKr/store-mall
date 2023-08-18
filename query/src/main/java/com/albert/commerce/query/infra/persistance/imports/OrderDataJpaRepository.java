package com.albert.commerce.query.infra.persistance.imports;

import com.albert.commerce.query.domain.order.OrderData;
import com.albert.commerce.query.domain.order.OrderId;
import com.albert.commerce.query.domain.user.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataJpaRepository extends JpaRepository<OrderData, OrderId> {

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<OrderData> findByUserId(UserId userId, Pageable pageable);
}
