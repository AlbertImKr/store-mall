package com.albert.commerce.comment.command.apllication;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import lombok.Builder;

@Builder
public record CommentResponse(
        CommentId commentId,
        StoreId storeId,
        ProductId productId,
        String userName,
        Comment parentComment,
        Comment childComment) {

    public static CommentResponse of(Comment comment, String nickname) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .storeId(comment.getStoreId())
                .productId(comment.getProductId())
                .userName(nickname)
                .parentComment(comment.getParentComment())
                .childComment(comment.getChlidComment())
                .build();
    }
}
