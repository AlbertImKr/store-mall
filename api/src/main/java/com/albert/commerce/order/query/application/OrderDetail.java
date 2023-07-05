package com.albert.commerce.order.query.application;

import com.albert.commerce.order.command.domain.DeliveryStatus;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public final class OrderDetail {

    private OrderId orderId;
    private UserId userId;
    private List<Product> products;
    private DeliveryStatus deliveryStatus;
    private long amount;
    private LocalDateTime createdTime;
    private StoreId storeId;


    @QueryProjection
    public OrderDetail(OrderId orderId, UserId userId, List<Product> products,
            DeliveryStatus deliveryStatus, long amount, LocalDateTime createdTime,
            StoreId storeId) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.deliveryStatus = deliveryStatus;
        this.amount = amount;
        this.createdTime = createdTime;
        this.storeId = storeId;
    }


}
