package com.albert.commerce.order.query.application;

import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
public class OrderFacade {

    private final OrderDao orderDao;
    private final UserDao userDao;

    @Transactional(readOnly = true)
    public OrderDetail findById(OrderId orderId, String userEmail) {
        return orderDao.findById(orderId, userEmail);
    }

    @Transactional(readOnly = true)
    public Page<OrderDetail> findAllByUserId(String userEmail, Pageable pageable) {
        User user = userDao.findByEmail(userEmail);
        return orderDao.findByUserId(user.getId(), pageable);
    }
}
