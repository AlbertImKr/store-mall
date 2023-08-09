package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class CommentResponse {

    private CommentId commentId;
    private StoreId storeId;
    private ProductId productId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private String nickname;
    private CommentId parentCommentId;
    private String detail;
    private UserId userId;

    @Builder
    private CommentResponse(CommentId commentId, StoreId storeId, ProductId productId,
            LocalDateTime createdTime, LocalDateTime updateTime, String nickname,
            CommentId parentCommentId,
            String detail, UserId userId) {
        this.commentId = commentId;
        this.storeId = storeId;
        this.productId = productId;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.nickname = nickname;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
        this.userId = userId;
    }
}
