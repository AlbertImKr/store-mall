package com.albert.commerce.comment.command.apllication;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;

public record CommentRequest(ProductId productId,
                             StoreId storeId,
                             CommentId parentCommentId) {

    public Comment toEntity(UserId userId, CommentId commentId) {
        return Comment.builder()
                .commentId(commentId)
                .userId(userId)
                .productId(productId)
                .storeId(storeId)
                .build();
    }
}
