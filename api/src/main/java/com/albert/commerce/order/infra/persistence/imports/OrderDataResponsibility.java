package com.albert.commerce.order.infra.persistence.imports;

import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.domain.OrderData;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface OrderDataResponsibility extends Repository<OrderData, ProductId> {

    List<OrderData> findByUserIdAndOrderId(UserId userId, OrderId orderId);

    Page<OrderData> findByUserId(UserId userId, Pageable pageable);
}
