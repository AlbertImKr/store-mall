package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.OrderDao;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.exception.error.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderFacade {

    private final OrderDao orderDao;

    public Order getById(String id) {
        return orderDao.findById(OrderId.from(id))
                .orElseThrow(OrderNotFoundException::new);
    }
}
