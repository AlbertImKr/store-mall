package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.OrderDao;
import com.albert.commerce.config.cache.CacheConfig;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.exception.error.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderFacade implements CacheConfig {

    private final OrderDao orderDao;

    @Cacheable(value = "ord", key = "#id")
    public Order getById(String id) {
        return orderDao.findById(OrderId.from(id))
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public String getCacheName() {
        return "ord";
    }

    @Override
    public long getTtl() {
        return 3600;
    }
}
