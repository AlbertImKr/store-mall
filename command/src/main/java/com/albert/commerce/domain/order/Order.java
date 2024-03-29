package com.albert.commerce.domain.order;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.units.DeliveryStatus;
import com.albert.commerce.domain.user.UserId;
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
import lombok.NoArgsConstructor;

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
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder(access = AccessLevel.PRIVATE)
    private Order(OrderId orderId, UserId userId, List<OrderLine> orderLines, StoreId storeId,
            LocalDateTime createdTime,
            LocalDateTime updatedTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderLines = orderLines;
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.storeId = storeId;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        Events.raise(toOrderCreatedEvent());
    }

    public static Order from(OrderId orderId, StoreId storeId, Map<String, Long> productsIdAndQuantity,
            List<Product> products, LocalDateTime createdTime, UserId userId) {
        List<OrderLine> orderLines = getOrderLines(productsIdAndQuantity, products);
        return Order.builder()
                .orderId(orderId)
                .storeId(storeId)
                .userId(userId)
                .orderLines(orderLines)
                .createdTime(createdTime)
                .build();
    }

    public void cancel(LocalDateTime updatedTime) {
        this.deliveryStatus = DeliveryStatus.CANCELED;
        this.updatedTime = updatedTime;
        Events.raise(toOrderCancelEvent());
    }

    public OrderId getOrderId() {
        return orderId;
    }

    private OrderPlacedEvent toOrderCreatedEvent() {
        return new OrderPlacedEvent(
                orderId,
                userId,
                storeId,
                toOrderDetailRequests(orderLines),
                deliveryStatus,
                createdTime,
                updatedTime
        );
    }

    private OrderCanceledEvent toOrderCancelEvent() {
        return new OrderCanceledEvent(this.orderId, this.updatedTime);
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

    private static List<OrderLine> getOrderLines(Map<String, Long> productsIdAndQuantity, List<Product> products) {
        return products.stream()
                .map(toOrderLine(productsIdAndQuantity))
                .toList();
    }

    private static Function<Product, OrderLine> toOrderLine(Map<String, Long> productsIdAndQuantity) {
        return product -> {
            Money price = product.getPrice();
            Long quantity = productsIdAndQuantity.get(product.getProductId().getValue());
            Money amount = price.multiply(quantity);
            return OrderLine.builder()
                    .productId(product.getProductId())
                    .price(price)
                    .quantity(quantity)
                    .amount(amount)
                    .build();
        };
    }
}
