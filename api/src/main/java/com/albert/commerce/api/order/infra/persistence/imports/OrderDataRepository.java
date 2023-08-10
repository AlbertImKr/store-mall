package com.albert.commerce.api.order.infra.persistence.imports;

import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.order.query.domain.OrderData;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataRepository extends JpaRepository<OrderData, ProductId> {

    List<OrderData> findByUserIdAndOrderId(DomainId userId, OrderId orderId);

    Page<OrderData> findByUserId(DomainId userId, Pageable pageable);
}
