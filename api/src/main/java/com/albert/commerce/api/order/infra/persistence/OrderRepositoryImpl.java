package com.albert.commerce.api.order.infra.persistence;

import com.albert.commerce.api.order.command.domain.Order;
import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.order.command.domain.OrderRepository;
import com.albert.commerce.api.order.infra.persistence.imports.OrderJpaRepository;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public Order save(Order order) {
        order.updateId(nextId(), LocalDateTime.now(), LocalDateTime.now());
        return orderJpaRepository.save(order);
    }

    @Override
    public boolean exist(OrderId orderId, UserId userId) {
        return orderJpaRepository.existsByOrderIdAndUserId(orderId, userId);
    }

    @Override
    public void deleteById(OrderId orderId) {
        orderJpaRepository.deleteById(orderId);
    }

    @Override
    public Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderJpaRepository.findByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public Page<Order> findAllByUserId(UserId userId, Pageable pageable) {
        return orderJpaRepository.findAllByUserId(userId, pageable);
    }

    private OrderId nextId() {
        String generate = sequenceGenerator.generate();
        return OrderId.from(generate);
    }
}
