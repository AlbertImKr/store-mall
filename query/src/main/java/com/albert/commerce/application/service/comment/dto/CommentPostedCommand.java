package com.albert.commerce.application.service.comment.dto;

import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDateTime;

public record CommentPostedCommand(
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
