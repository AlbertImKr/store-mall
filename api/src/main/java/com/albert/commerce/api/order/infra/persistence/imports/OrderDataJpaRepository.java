package com.albert.commerce.api.order.infra.persistence.imports;

import com.albert.commerce.api.order.query.domain.OrderData;
import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataJpaRepository extends JpaRepository<OrderData, DomainId> {

    List<OrderData> findByUserIdAndOrderId(DomainId userId, DomainId orderId);

    Page<OrderData> findByUserId(DomainId userId, Pageable pageable);
}
