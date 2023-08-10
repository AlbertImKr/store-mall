package com.albert.commerce.api.order.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.infra.persistence.converters.MoneyConverter;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderLine {

    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    @Embedded
    private DomainId productId;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    @Column(name = "quantity")
    private long quantity;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;

    @Builder
    public OrderLine(DomainId productId, Money price, long quantity, Money amount) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }
}
