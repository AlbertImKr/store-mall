package com.albert.commerce.adapter.in.messaging.listener.domainevent.dto;

import com.albert.commerce.domain.event.DomainEventDTO;
import com.albert.commerce.domain.store.StoreId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Builder;

@DomainEventDTO
@Builder
public record StoreUploadedEvent(
        StoreId storeId,
        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime updatedTime
) {

}
