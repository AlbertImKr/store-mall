package com.albert.commerce.order.command.application;

import com.albert.commerce.order.command.domain.DeliveryStatus;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public record OrderResponse(OrderId orderId,
                            UserId userId,
                            List<Product> products,
                            DeliveryStatus deliveryStatus,
                            StoreId storeId,
                            long amount,
                            LocalDateTime createdTime) {

    @Builder
    public OrderResponse(OrderId orderId, UserId userId, List<Product> products,
            DeliveryStatus deliveryStatus, StoreId storeId, long amount,
            LocalDateTime createdTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.deliveryStatus = deliveryStatus;
        this.storeId = storeId;
        this.amount = amount;
        this.createdTime = createdTime;
    }

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .products(order.getProducts())
                .deliveryStatus(order.getDeliveryStatus())
                .storeId(order.getStoreId())
                .amount(order.getAmount())
                .createdTime(order.getCreatedTime())
                .build();
    }
}
