package com.albert.commerce.application.query.order;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.domain.command.order.OrderLine;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.query.product.ProductData;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderLineDetail {

    private ProductId productId;

    private Money price;

    private long quantity;

    private Money amount;

    private String productName;
    private String productDescription;

    @Builder
    public OrderLineDetail(ProductId productId, Money price, long quantity, Money amount, String productName,
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
