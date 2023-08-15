package com.albert.commerce.api.order.infra.persistence;

import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.order.infra.persistence.imports.OrderDataJpaRepository;
import com.albert.commerce.api.order.query.domain.OrderDao;
import com.albert.commerce.api.order.query.domain.OrderData;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {

    private final OrderDataJpaRepository orderDataJpaRepository;

    @Override
    public Page<OrderData> findAllByUserId(UserId userId, Pageable pageable) {
        return orderDataJpaRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderDataJpaRepository.findByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public OrderData save(OrderData order) {
        return orderDataJpaRepository.save(order);
    }

    @Override
    public Optional<OrderData> findById(OrderId orderId) {
        return orderDataJpaRepository.findById(orderId);
    }
}
