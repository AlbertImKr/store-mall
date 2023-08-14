package com.albert.commerce.api.order.query.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Page<OrderData> findAllByUserId(DomainId userId, Pageable pageable);

    List<OrderData> findByUserIdAndOrderId(DomainId userId, DomainId orderId);

    OrderData save(OrderData order);

    Optional<OrderData> findById(DomainId orderId);
}
