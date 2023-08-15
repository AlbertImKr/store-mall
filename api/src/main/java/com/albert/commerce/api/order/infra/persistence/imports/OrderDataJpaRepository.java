package com.albert.commerce.api.order.infra.persistence.imports;

import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.order.query.domain.OrderData;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataJpaRepository extends JpaRepository<OrderData, OrderId> {

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<OrderData> findByUserId(UserId userId, Pageable pageable);
}
