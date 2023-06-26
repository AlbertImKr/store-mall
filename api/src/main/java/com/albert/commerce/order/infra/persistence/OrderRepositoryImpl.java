package com.albert.commerce.order.infra.persistence;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.OrderRepository;
import com.albert.commerce.order.infra.persistence.imports.OrderJpaResponsibility;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaResponsibility orderJpaResponsibility;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public Optional<Order> findById(OrderId orderId, String userEmail) {
        return orderJpaResponsibility.findById(orderId);
    }

    @Override
    public Order save(Order order) {
        return orderJpaResponsibility.save(order);
    }

    @Override
    public OrderId nextId() {
        String generate = sequenceGenerator.generate();
        return OrderId.from(generate);
    }

    @Override
    public void delete(Order order) {
        orderJpaResponsibility.delete(order);
    }
}
