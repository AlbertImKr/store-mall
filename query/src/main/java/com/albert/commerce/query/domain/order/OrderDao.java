package com.albert.commerce.query.domain.order;

import com.albert.commerce.query.domain.user.UserId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Page<OrderData> findAllByUserId(UserId userId, Pageable pageable);

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    OrderData save(OrderData order);

    Optional<OrderData> findById(OrderId orderId);
}
