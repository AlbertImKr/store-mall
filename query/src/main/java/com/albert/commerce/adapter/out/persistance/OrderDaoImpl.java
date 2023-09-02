package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.OrderJpaRepository;
import com.albert.commerce.application.port.out.OrderDao;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId);
    }
}
