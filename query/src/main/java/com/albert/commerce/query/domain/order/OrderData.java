package com.albert.commerce.query.domain.order;

import com.albert.commerce.query.common.units.DeliveryStatus;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserId;
import com.albert.commerce.query.infra.persistance.Money;
import com.albert.commerce.query.infra.persistance.MoneyConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_query")
public class OrderData {

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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime createdTime;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updateTime;
    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Builder
    private OrderData(OrderId orderId, UserId userId, String orderUserNickName, StoreId storeId, String storeName,
            OrderDetails orderDetails, DeliveryStatus deliveryStatus, Money amount, LocalDateTime createdTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderUserNickName = orderUserNickName;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orderDetails = orderDetails;
        this.deliveryStatus = deliveryStatus;
        this.amount = amount;
        this.createdTime = createdTime;
    }

    public void cancel(LocalDateTime updatedTime) {
        this.deliveryStatus = DeliveryStatus.CANCELED;
        this.updateTime = updatedTime;
    }
}
