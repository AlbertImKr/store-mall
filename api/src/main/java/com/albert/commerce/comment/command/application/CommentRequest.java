package com.albert.commerce.comment.command.application;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;

public record CommentRequest(String productId,
                             String storeId,
                             String parentCommentId,
                             String detail) {

    public Comment toEntity(ProductId productId, StoreId storeId, UserId userId, CommentId parentCommentId) {
        return Comment.builder()
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(detail)
                .parentCommentId(parentCommentId)
                .build();
    }
}
