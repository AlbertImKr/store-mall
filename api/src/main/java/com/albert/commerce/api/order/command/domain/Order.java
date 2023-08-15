package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.units.DeliveryStatus;
import com.albert.commerce.shared.messaging.domain.event.Events;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase_order")
public class Order {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "order_id", nullable = false))
    private OrderId orderId;
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private UserId userId;
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "order_line_id")
    private List<OrderLine> orderLines;
    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime createdTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updateTime;

    @Builder
    private Order(OrderId orderId, UserId userId, List<OrderLine> orderLines, StoreId storeId) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderLines = orderLines;
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.storeId = storeId;
    }

    public void updateId(OrderId orderId, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.orderId = orderId;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        Events.raise(toOrderCreatedEvent());
    }

    public void cancel(LocalDateTime updateTime) {
        this.deliveryStatus = DeliveryStatus.CANCELED;
        this.updateTime = updateTime;
        Events.raise(toOrderCancelEvent());
    }

    private OrderCreatedEvent toOrderCreatedEvent() {
        return OrderCreatedEvent.builder()
                .orderId(orderId)
                .userId(userId)
                .storeId(storeId)
                .orderLines(orderLines)
                .deliveryStatus(deliveryStatus)
                .createdTime(createdTime)
                .updateTime(updateTime)
                .build();
    }

    private OrderCancelEvent toOrderCancelEvent() {
        return new OrderCancelEvent(this.orderId, this.updateTime);
    }
}
