package com.albert.commerce.api.order.query.application;

import com.albert.commerce.api.order.command.domain.OrderLine;
import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderLineDetail {

    private DomainId productId;

    private Money price;

    private long quantity;

    private Money amount;

    private String productName;
    private String productDescription;

    @Builder
    public OrderLineDetail(DomainId productId, Money price, long quantity, Money amount, String productName,
            String productDescription) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.productName = productName;
        this.productDescription = productDescription;
    }


    public static OrderLineDetail of(OrderLine orderLine, ProductData productData) {
        return OrderLineDetail.builder()
                .productId(orderLine.getProductId())
                .price(orderLine.getPrice())
                .quantity(orderLine.getQuantity())
                .amount(orderLine.getAmount())
                .productName(productData.getProductName())
                .productDescription(productData.getDescription())
                .build();
    }
}