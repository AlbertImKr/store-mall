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
                            long amount,
                            LocalDateTime createdTime,
                            StoreId storeId) {

    @Builder
    public OrderResponse {
    }

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .products(order.getProducts())
                .storeId(order.getStoreId())
                .deliveryStatus(order.getDeliveryStatus())
                .amount(order.getAmount())
                .createdTime(order.getCreatedTime())
                .build();
    }

}
