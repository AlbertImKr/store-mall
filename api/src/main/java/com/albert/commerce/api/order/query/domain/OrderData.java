package com.albert.commerce.api.order.query.domain;

import com.albert.commerce.api.order.command.domain.DeliveryStatus;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.infra.persistence.converters.MoneyConverter;
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
    private DomainId orderId;
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private DomainId userId;
    @Column(name = "order_user_nickname")
    private String orderUserNickName;
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private DomainId storeId;
    @Column(name = "store_name")
    private String storeName;
    @Embedded
    private OrderDetails orderDetails;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Builder
    private OrderData(DomainId orderId, DomainId userId, String orderUserNickName, DomainId storeId, String storeName,
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
}
