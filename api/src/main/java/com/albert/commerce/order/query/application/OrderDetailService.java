package com.albert.commerce.order.query.application;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderDetailService {

    private final OrderDao orderDao;

    public OrderDetail findById(OrderId orderId, String userEmail) {
        Order order = orderDao.findById(orderId, userEmail);
        return OrderDetail.from(order);
    }

    public Page<OrderDetail> findAllByUserId(UserId userId, Pageable pageable) {
        return orderDao.findByUserId(userId, pageable);
    }
}
