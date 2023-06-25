package com.albert.commerce.order.query.application;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.domain.OrderDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderDetailService {

    private final OrderDao orderDao;

    @Transactional
    public OrderDetail findById(OrderId orderId, String userEmail) {
        Order order = orderDao.findById(orderId, userEmail);
        return OrderDetail.from(order);
    }
}
