package com.albert.commerce.order.query.domain;

import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Page<OrderData> findAllByUserId(UserId userId, Pageable pageable);

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);
}
