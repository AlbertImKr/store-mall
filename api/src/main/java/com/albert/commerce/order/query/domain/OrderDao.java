package com.albert.commerce.order.query.domain;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;

public interface OrderDao {

    Order findById(OrderId orderId, String userEmail);
}
