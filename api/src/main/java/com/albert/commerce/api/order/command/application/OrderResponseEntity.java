package com.albert.commerce.api.order.command.application;

import com.albert.commerce.api.order.command.domain.DeliveryStatus;
import com.albert.commerce.api.product.command.application.dto.ProductResponse;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class OrderResponseEntity extends RepresentationModel<ProductResponse> {

    private final DomainId orderId;
    private final DomainId userId;
    private final DomainId storeId;
    private final DomainId productId;
    private final DeliveryStatus deliveryStatus;
    private final Money amount;
    private final LocalDateTime createdTime;

    @Builder
    protected OrderResponseEntity(Links links, DomainId orderId,
            DomainId userId, DomainId storeId, DomainId productId,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderResponseEntity that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(getUserId(),
                that.getUserId()) && Objects.equals(getStoreId(), that.getStoreId()) && Objects.equals(
                getProductId(), that.getProductId()) && getDeliveryStatus() == that.getDeliveryStatus()
                && Objects.equals(getAmount(), that.getAmount()) && Objects.equals(getCreatedTime(),
                that.getCreatedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrderId(), getUserId(), getStoreId(), getProductId(),
                getDeliveryStatus(),
                getAmount(), getCreatedTime());
    }
}