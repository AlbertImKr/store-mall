package com.albert.commerce.api.order.query.domain;

import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Page<OrderData> findAllByUserId(DomainId userId, Pageable pageable);

    List<OrderData> findByUserIdAndOrderId(DomainId userId, OrderId orderId);
}
