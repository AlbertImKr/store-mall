package com.albert.commerce.order.query.application;

import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
public class OrderFacade {

    private final OrderDao orderDao;

    @Transactional(readOnly = true)
    public OrderDetail findById(OrderId orderId, String userEmail) {
        return orderDao.findById(orderId, userEmail);
    }

    @Transactional(readOnly = true)
    public Page<OrderDetail> findAllByUserId(UserId userId, Pageable pageable) {
        return orderDao.findByUserId(userId, pageable);
    }
}
