package com.albert.commerce.query.application.comment.dto;

import com.albert.commerce.query.domain.comment.CommentId;
import com.albert.commerce.query.domain.product.ProductId;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserId;
import java.time.LocalDateTime;

public record CommentCreatedEvent(
        CommentId commentId,
        ProductId productId,
        StoreId storeId,
        UserId userId,
        CommentId parentCommentId,
        String detail,
        LocalDateTime createdTime,
        LocalDateTime updatedTime
) {

}
