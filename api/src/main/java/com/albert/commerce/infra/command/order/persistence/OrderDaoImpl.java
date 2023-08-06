package com.albert.commerce.infra.command.order.persistence;

import com.albert.commerce.domain.command.order.OrderId;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.order.OrderDao;
import com.albert.commerce.domain.query.order.OrderData;
import com.albert.commerce.infra.command.order.persistence.imports.OrderDataRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {

    private final OrderDataRepository orderDataRepository;

    @Override
    public Page<OrderData> findAllByUserId(UserId userId, Pageable pageable) {
        return orderDataRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return orderDataRepository.findByUserIdAndOrderId(userId, orderId);
    }
}
