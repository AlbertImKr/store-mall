package com.albert.commerce.api.order.command.application;

import com.albert.commerce.api.common.infra.persistence.Money;
import com.albert.commerce.api.order.command.domain.DeliveryStatus;
import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.order.query.domain.OrderData;
import com.albert.commerce.api.order.ui.OrderController;
import com.albert.commerce.api.product.command.application.dto.ProductResponse;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Getter
public class OrderResponseEntity extends RepresentationModel<ProductResponse> {

    private final OrderId orderId;
    private final UserId userId;
    private final StoreId storeId;
    private final ProductId productId;
    private final DeliveryStatus deliveryStatus;
    private final Money amount;
    private final LocalDateTime createdTime;

    @Builder
    protected OrderResponseEntity(Links links, OrderId orderId,
            UserId userId, StoreId storeId, ProductId productId,
            DeliveryStatus deliveryStatus, Money amount, LocalDateTime createdTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.productId = productId;
        this.deliveryStatus = deliveryStatus;
        this.amount = amount;
        this.createdTime = createdTime;
        this.add(links);
    }

    public static OrderResponseEntity from(OrderData order) {
        return OrderResponseEntity.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .storeId(order.getStoreId())
                .deliveryStatus(order.getDeliveryStatus())
                .amount(order.getAmount())
                .productId(order.getProductId())
                .links(Links.of(WebMvcLinkBuilder.linkTo(OrderController.class)
                        .slash(order.getOrderId().getId())
                        .withSelfRel()))
                .createdTime(order.getCreatedTime())
                .build();
    }
}
