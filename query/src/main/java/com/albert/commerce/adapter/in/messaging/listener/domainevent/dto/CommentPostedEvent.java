package com.albert.commerce.adapter.in.messaging.listener.domainevent.dto;

import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.event.DomainEventDTO;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;

@DomainEventDTO
public record CommentPostedEvent(
        CommentId commentId,
        ProductId productId,
        StoreId storeId,
        UserId userId,
        CommentId parentCommentId,
        String detail,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime createdTime,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime updatedTime
) {

}
