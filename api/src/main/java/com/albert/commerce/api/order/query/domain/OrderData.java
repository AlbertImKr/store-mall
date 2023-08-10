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

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "order_id", nullable = false))
    private DomainId orderId;
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private DomainId userId;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "product_name")
    private String productName;

    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private DomainId storeId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private DomainId productId;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;

    @Builder
    private OrderData(DomainId orderId, DomainId userId, LocalDateTime createdTime, String nickname, String productName,
            DomainId storeId, DomainId productsId, DeliveryStatus deliveryStatus, Money amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.createdTime = createdTime;
        this.nickname = nickname;
        this.productName = productName;
        this.storeId = storeId;
        this.productId = productsId;
        this.deliveryStatus = deliveryStatus;
        this.amount = amount;
    }

}
