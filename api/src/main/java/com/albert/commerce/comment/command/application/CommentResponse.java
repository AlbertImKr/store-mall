package com.albert.commerce.comment.command.application;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
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

    public CommentResponse() {
    }

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

    public static CommentResponse of(Comment comment, String nickname) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .nickname(nickname)
                .storeId(comment.getStoreId())
                .productId(comment.getProductId())
                .createdTime(comment.getCreatedTime())
                .updateTime(comment.getUpdateTime())
                .parentCommentId(comment.getParentCommentId())
                .detail(comment.getDetail())
                .build();
    }
}
