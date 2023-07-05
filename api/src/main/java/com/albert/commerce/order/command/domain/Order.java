package com.albert.commerce.order.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "\"order\"")
public class Order extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "order_id", nullable = false))
    private OrderId orderId;
    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private UserId userId;
    @AttributeOverride(name = "id", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @ElementCollection
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    )
    private List<ProductId> productsId;
    private DeliveryStatus deliveryStatus;
    private long amount;

    @Builder
    protected Order(OrderId orderId, UserId userId, List<ProductId> productsId, StoreId storeId,
            long amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.productsId = productsId;
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.amount = amount;
        this.storeId = storeId;
    }
}
