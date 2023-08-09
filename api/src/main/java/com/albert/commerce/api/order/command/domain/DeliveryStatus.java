package com.albert.commerce.api.order.command.domain;

public enum DeliveryStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    FAILED,
    CANCELED
}
