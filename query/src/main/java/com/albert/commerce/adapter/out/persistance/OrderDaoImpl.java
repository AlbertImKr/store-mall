package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.OrderDataJpaRepository;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderDao;
import com.albert.commerce.domain.order.OrderId;
import com.albert.commerce.domain.user.UserId;
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
    public Page<Order> findAllByUserId(UserId userId, Pageable pageable) {
        return orderDataJpaRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderDataJpaRepository.findByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public Order save(Order order) {
        return orderDataJpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderDataJpaRepository.findById(orderId);
    }
}
