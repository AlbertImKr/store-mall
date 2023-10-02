package com.albert.commerce.domain.comment;

import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment")
public class Comment {

    protected LocalDateTime createdTime;
    protected LocalDateTime updatedTime;
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "comment_id", nullable = false))
    private CommentId commentId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    private StoreId storeId;
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    @Embedded
    private UserId userId;
    @AttributeOverride(name = "value", column = @Column(name = "parent_comment_id"))
    @Embedded
    private CommentId parentCommentId;
    private String detail;

    @Builder
    private Comment(CommentId commentId, ProductId productId, StoreId storeId,
            UserId userId, CommentId parentCommentId, String detail, LocalDateTime createdTime,
            LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        Events.raise(toCommentCreatedEvent());
    }

    public static Comment from(CommentId commentId, ProductId productId, StoreId storeId, UserId userId, String detail,
            CommentId parentCommentId, LocalDateTime createdTime) {
        return Comment.builder()
                .commentId(commentId)
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(detail)
                .parentCommentId(parentCommentId)
                .createdTime(createdTime)
                .build();
    }

    public void update(String detail, LocalDateTime updateTime) {
        this.detail = detail;
        this.updatedTime = updateTime;
        Events.raise(toCommentUpdatedEvent());
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public void delete(LocalDateTime updateTime) {
        this.detail = "";
        this.userId = null;
        this.updatedTime = updateTime;
        Events.raise(toCommentDeletedEvent());
    }

    private CommentPostedEvent toCommentCreatedEvent() {
        return new CommentPostedEvent(
                commentId,
                productId,
                storeId,
                userId,
                parentCommentId,
                detail,
                createdTime,
                updatedTime
        );
    }

    private CommentUpdatedEvent toCommentUpdatedEvent() {
        return new CommentUpdatedEvent(commentId, this.detail, this.updatedTime);
    }

    private CommentDeletedEvent toCommentDeletedEvent() {
        return new CommentDeletedEvent(this.commentId, this.updatedTime);
    }
}
