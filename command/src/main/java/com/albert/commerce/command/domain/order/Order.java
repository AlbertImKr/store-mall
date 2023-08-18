package com.albert.commerce.command.domain.order;

import com.albert.commerce.command.domain.product.Product;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.User;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.common.infra.persistence.Money;
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
import java.util.Map;
import java.util.function.Function;
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

    public static Order from(User user, StoreId storeId, Map<String, Long> productsIdAndQuantity,
            List<Product> products) {
        return Order.builder()
                .storeId(storeId)
                .userId(user.getUserId())
                .orderLines(getOrderLines(productsIdAndQuantity, products))
                .build();
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
                .orderDetailRequests(toOrderDetailRequests(orderLines))
                .deliveryStatus(deliveryStatus)
                .createdTime(createdTime)
                .updateTime(updateTime)
                .build();
    }

    private static List<OrderDetailRequest> toOrderDetailRequests(List<OrderLine> orderLines) {
        return orderLines.stream()
                .map(Order::toOrderDetailRequests)
                .toList();
    }

    private static OrderDetailRequest toOrderDetailRequests(OrderLine orderLine) {
        return new OrderDetailRequest(
                orderLine.getProductId(),
                orderLine.getPrice(),
                orderLine.getQuantity(),
                orderLine.getAmount()
        );
    }

    private OrderCancelEvent toOrderCancelEvent() {
        return new OrderCancelEvent(this.orderId, this.updateTime);
    }

    private static List<OrderLine> getOrderLines(Map<String, Long> productsIdAndQuantity, List<Product> products) {
        return products.stream()
                .map(toOrderLine(productsIdAndQuantity))
                .toList();
    }

    private static Function<Product, OrderLine> toOrderLine(Map<String, Long> productsIdAndQuantity) {
        return productData -> {
            Money price = productData.getPrice();
            Long quantity = productsIdAndQuantity.get(productData.getProductId().getValue());
            Money amount = price.multiply(quantity);
            return OrderLine.builder()
                    .productId(productData.getProductId())
                    .price(price)
                    .quantity(quantity)
                    .amount(amount)
                    .build();
        };
    }
}
