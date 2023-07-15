package com.albert.commerce.order.infra.persistence;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.OrderRepository;
import com.albert.commerce.order.infra.persistence.imports.OrderJpaResponsibility;
import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaResponsibility orderJpaResponsibility;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public Order save(Order order) {
        order.updateId(nextId());
        return orderJpaResponsibility.save(order);
    }

    private OrderId nextId() {
        String generate = sequenceGenerator.generate();
        return OrderId.from(generate);
    }

    @Override
    public boolean exist(OrderId orderId, UserId userId) {
        return orderJpaResponsibility.existsByOrderIdAndUserId(orderId, userId);
    }

    @Override
    public void deleteById(OrderId orderId) {
        orderJpaResponsibility.deleteById(orderId);
    }

    @Override
    public Optional<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderJpaResponsibility.findByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public Page<Order> findAllByUserId(UserId userId, Pageable pageable) {
        return orderJpaResponsibility.findAllByUserId(userId, pageable);
    }
}
