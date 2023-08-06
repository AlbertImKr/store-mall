package com.albert.commerce.infra.command.order.persistence.imports;

import com.albert.commerce.domain.command.order.OrderId;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.order.OrderData;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface OrderDataRepository extends Repository<OrderData, ProductId> {

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<OrderData> findByUserId(UserId userId, Pageable pageable);
}
