package com.albert.commerce.api.order.query.domain;

import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Page<OrderData> findAllByUserId(UserId userId, Pageable pageable);

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);
}
