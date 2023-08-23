package com.albert.commerce.adapter.in.messaging.listener.domainevent.dto;

import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.product.ProductId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;

public record ProductUpdatedEvent(
        ProductId productId,
        String productName,
        Money price,
        String brand,
        String category,
        String description,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime updatedTime
) {

}
