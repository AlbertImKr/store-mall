package com.albert.commerce.api.order.query.domain;

import com.albert.commerce.api.common.infra.persistence.Money;
import com.albert.commerce.api.common.infra.persistence.converters.MoneyConverter;
import com.albert.commerce.api.order.command.domain.DeliveryStatus;
import com.albert.commerce.api.order.command.domain.OrderId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.springframework.data.annotation.Immutable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Immutable
@Subselect("""
        select
             o.order_id,
             o.store_id as store_id,
             p.product_id as product_id,
             o.user_id as user_id,
             o.created_time,
             u.nickname as nickname,
             p.product_name,
             o.delivery_status,
             ol.amount
        from
             order_line ol
                 join purchase_order o
             on  o.order_id = ol.order_id
                 join user u
             on o.user_id = u.user_id
                 join product p
             on ol.product_id = p.product_id
        """
)
@Synchronize({"purchase_order", "order_products_id", "product", "user"})
public class OrderData {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "order_id", nullable = false))
    private OrderId orderId;
    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private UserId userId;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "product_name")
    private String productName;

    @AttributeOverride(name = "id", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;

    @Builder
    public OrderData(OrderId orderId, UserId userId, LocalDateTime createdTime, String nickname, String productName,
            StoreId storeId, ProductId productsId, DeliveryStatus deliveryStatus, Money amount) {
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
