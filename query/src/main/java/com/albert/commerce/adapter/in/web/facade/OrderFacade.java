package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.adapter.out.config.cache.CacheValue;
import com.albert.commerce.application.port.out.OrderDao;
import com.albert.commerce.application.service.exception.error.OrderNotFoundException;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderFacade {

    private final OrderDao orderDao;

    @Cacheable(value = CacheValue.ORDER)
    public Order getById(String id) {
        return orderDao.findById(OrderId.from(id))
                .orElseThrow(OrderNotFoundException::new);
    }
}
