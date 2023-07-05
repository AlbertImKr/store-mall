package com.albert.commerce.order.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
    @ManyToMany
    private List<Product> products;
    private DeliveryStatus deliveryStatus;
    private long amount;

    @Builder
    protected Order(OrderId orderId, UserId userId, List<Product> products, StoreId storeId,
            long amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.amount = amount;
        this.storeId = storeId;
    }
}
