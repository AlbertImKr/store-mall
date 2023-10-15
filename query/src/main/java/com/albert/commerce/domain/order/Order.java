package com.albert.commerce.domain.order;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.adapter.out.persistence.converters.MoneyConverter;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.units.DeliveryStatus;
import com.albert.commerce.domain.user.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase_order")
public class Order implements Serializable {

    @AttributeOverride(name = "value", column = @Column(name = "order_id", nullable = false))
    @EmbeddedId
    private OrderId orderId;
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private UserId userId;
    @Column(name = "order_user_nickname")
    private String orderUserNickName;
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @Column(name = "store_name")
    private String storeName;
    @Embedded
    private OrderDetails orderDetails;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime createdTime;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private LocalDateTime updatedTime;
    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Builder
    private Order(OrderId orderId, UserId userId, String orderUserNickName, StoreId storeId, String storeName,
            OrderDetails orderDetails, DeliveryStatus deliveryStatus, Money amount, LocalDateTime createdTime,
            LocalDateTime updatedTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderUserNickName = orderUserNickName;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orderDetails = orderDetails;
        this.deliveryStatus = deliveryStatus;
        this.amount = amount;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public void cancel(LocalDateTime updatedTime) {
        this.deliveryStatus = DeliveryStatus.CANCELED;
        this.updatedTime = updatedTime;
    }
}
