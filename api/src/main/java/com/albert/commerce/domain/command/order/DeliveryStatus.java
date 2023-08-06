package com.albert.commerce.domain.command.order;

public enum DeliveryStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    FAILED,
    CANCELED
}
