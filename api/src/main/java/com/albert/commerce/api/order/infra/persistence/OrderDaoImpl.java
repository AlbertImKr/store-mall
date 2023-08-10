package com.albert.commerce.api.order.infra.persistence;

import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.order.infra.persistence.imports.OrderDataRepository;
import com.albert.commerce.api.order.query.domain.OrderDao;
import com.albert.commerce.api.order.query.domain.OrderData;
import com.albert.commerce.common.domain.DomainId;
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
    public Page<OrderData> findAllByUserId(DomainId userId, Pageable pageable) {
        return orderDataRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<OrderData> findByUserIdAndOrderId(DomainId userId, OrderId orderId) {
        return orderDataRepository.findByUserIdAndOrderId(userId, orderId);
    }
}
