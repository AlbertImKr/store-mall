package com.albert.commerce.api.comment.query.application.dto;

import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;
import lombok.Builder;

public class CommentCreatedRequest {

    private final DomainId commentId;
    private final DomainId productId;
    private final DomainId storeId;
    private final DomainId userId;
    private final DomainId parentCommentId;
    private final String detail;
    private final LocalDateTime createdTime;
    private final LocalDateTime updateTime;

    @Builder
    private CommentCreatedRequest(DomainId commentId, DomainId productId, DomainId storeId, DomainId userId,
            DomainId parentCommentId, String detail, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }

    public DomainId getCommentId() {
        return commentId;
    }

    public DomainId getProductId() {
        return productId;
    }

    public DomainId getStoreId() {
        return storeId;
    }

    public DomainId getUserId() {
        return userId;
    }

    public DomainId getParentCommentId() {
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
