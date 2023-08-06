package com.albert.commerce.domain.query.order;

import com.albert.commerce.domain.command.order.OrderId;
import com.albert.commerce.domain.command.user.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Page<OrderData> findAllByUserId(UserId userId, Pageable pageable);

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);
}
