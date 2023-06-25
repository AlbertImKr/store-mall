package com.albert.commerce.order.query.application;

import com.albert.commerce.order.command.domain.DeliveryStatus;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderDetail(OrderId orderId,
                          UserId userId,
                          List<Product> products,
                          DeliveryStatus deliveryStatus,
                          long amount,
                          LocalDateTime createdTime,
                          StoreId storeId) {


    public static OrderDetail from(Order order) {
        return OrderDetail.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .storeId(order.getStoreId())
                .products(order.getProducts())
                .deliveryStatus(order.getDeliveryStatus())
                .amount(order.getAmount())
                .createdTime(order.getCreatedTime())
                .build();
    }
}
