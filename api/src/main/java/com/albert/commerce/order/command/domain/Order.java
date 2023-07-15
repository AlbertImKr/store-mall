package com.albert.commerce.order.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase_order")
public class Order extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "order_id", nullable = false))
    private OrderId orderId;
    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private UserId userId;
    @AttributeOverride(name = "id", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "order_line_id")
    private List<OrderLine> orderLines;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Builder
    private Order(OrderId orderId, UserId userId, List<OrderLine> orderLines, StoreId storeId) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderLines = orderLines;
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.storeId = storeId;
    }

    public void updateId(OrderId orderId) {
        this.orderId = orderId;
    }
}
