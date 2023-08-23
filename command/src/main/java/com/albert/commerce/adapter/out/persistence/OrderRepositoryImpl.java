package com.albert.commerce.adapter.out.persistence;

import com.albert.commerce.adapter.out.persistence.imports.OrderJpaRepository;
import com.albert.commerce.application.port.out.OrderRepository;
import com.albert.commerce.application.port.out.persistence.SequenceGenerator;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderJpaRepository.findByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public OrderId nextId() {
        String generate = sequenceGenerator.generate();
        return OrderId.from(generate);
    }
}
