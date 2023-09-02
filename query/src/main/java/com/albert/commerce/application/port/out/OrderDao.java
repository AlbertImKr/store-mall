package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import java.util.Optional;

public interface OrderDao {

    Optional<Order> findById(OrderId orderId);
}
