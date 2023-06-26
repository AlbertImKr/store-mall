package com.albert.commerce.order.query.domain;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.user.command.domain.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Order findById(OrderId orderId, String userEmail);

    Page<OrderDetail> findByUserId(UserId userId, Pageable pageable);
}
