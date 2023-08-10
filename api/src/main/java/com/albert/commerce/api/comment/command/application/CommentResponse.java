package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class CommentResponse {

    private CommentId commentId;
    private DomainId storeId;
    private ProductId productId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private String nickname;
    private CommentId parentCommentId;
    private String detail;
    private DomainId userId;

    @Builder
    private CommentResponse(CommentId commentId, DomainId storeId, ProductId productId,
            LocalDateTime createdTime, LocalDateTime updateTime, String nickname,
            CommentId parentCommentId,
            String detail, DomainId userId) {
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
