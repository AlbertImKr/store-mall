package com.albert.commerce.domain.comment;

import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;

public class CommentPostedEvent extends DomainEvent {

    private final CommentId commentId;
    private final ProductId productId;
    private final StoreId storeId;
    private final UserId userId;
    private final CommentId parentCommentId;
    private final String detail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    @Builder
    private CommentPostedEvent(CommentId commentId, ProductId productId, StoreId storeId, UserId userId,
            CommentId parentCommentId, String detail, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
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

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
