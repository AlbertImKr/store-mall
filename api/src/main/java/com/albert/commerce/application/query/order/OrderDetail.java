package com.albert.commerce.application.query.order;

import com.albert.commerce.domain.command.order.DeliveryStatus;
import com.albert.commerce.domain.command.order.Order;
import com.albert.commerce.domain.command.order.OrderId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
public final class OrderDetail extends RepresentationModel<OrderDetail> {

    private OrderId orderId;
    private UserId userId;
    private DeliveryStatus deliveryStatus;
    private List<OrderLineDetail> orderLineDetails;
    private LocalDateTime createdTime;
    private StoreId storeId;


    @QueryProjection
    public OrderDetail(OrderId orderId, UserId userId,
            DeliveryStatus deliveryStatus, List<OrderLineDetail> orderLineDetails, LocalDateTime createdTime,
            StoreId storeId) {
        this.orderId = orderId;
        this.userId = userId;
        this.deliveryStatus = deliveryStatus;
        this.orderLineDetails = orderLineDetails;
        this.createdTime = createdTime;
        this.storeId = storeId;
    }


    public static OrderDetail of(Order order, List<OrderLineDetail> orderLineDetails) {
        return OrderDetail.builder()
                .storeId(order.getStoreId())
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .deliveryStatus(order.getDeliveryStatus())
                .orderLineDetails(orderLineDetails)
                .build();
    }
}
