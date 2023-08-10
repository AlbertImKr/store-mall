package com.albert.commerce.api.order.query.application;

import com.albert.commerce.api.order.command.domain.DeliveryStatus;
import com.albert.commerce.api.order.command.domain.Order;
import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.domain.DomainId;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private DomainId storeId;


    @QueryProjection
    public OrderDetail(OrderId orderId, UserId userId,
            DeliveryStatus deliveryStatus, List<OrderLineDetail> orderLineDetails, LocalDateTime createdTime,
            DomainId storeId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetail that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(getUserId(),
                that.getUserId()) && getDeliveryStatus() == that.getDeliveryStatus() && Objects.equals(
                getOrderLineDetails(), that.getOrderLineDetails()) && Objects.equals(getCreatedTime(),
                that.getCreatedTime()) && Objects.equals(getStoreId(), that.getStoreId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrderId(), getUserId(), getDeliveryStatus(), getOrderLineDetails(),
                getCreatedTime(), getStoreId());
    }
}
