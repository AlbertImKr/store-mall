package com.albert.commerce.api.comment.query.application.dto;

import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;

public class CommentCreatedRequest {

    private final CommentId commentId;
    private final ProductId productId;
    private final StoreId storeId;
    private final UserId userId;
    private final CommentId parentCommentId;
    private final String detail;
    private final LocalDateTime createdTime;
    private final LocalDateTime updateTime;

    @Builder
    private CommentCreatedRequest(CommentId commentId, ProductId productId, StoreId storeId, UserId userId,
            CommentId parentCommentId, String detail, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public StoreId getStoreId() {
        return storeId;
    }

    public UserId getUserId() {
        return userId;
    }

    public CommentId getParentCommentId() {
        return parentCommentId;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
