package com.albert.commerce.domain.comment;

import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime createdTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updateTime;

    @Builder
    private Comment(CommentId commentId, ProductId productId, StoreId storeId,
            UserId userId, CommentId parentCommentId, String detail) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
    }

    public void updateId(CommentId commentId, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.commentId = commentId;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        Events.raise(toCommentCreatedEvent());
    }

    public void update(String detail, LocalDateTime updateTime) {
        this.detail = detail;
        this.updateTime = updateTime;
        Events.raise(toCommentUpdatedEvent());
    }

    private CommentPostedEvent toCommentCreatedEvent() {
        return CommentPostedEvent.builder()
                .commentId(commentId)
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .parentCommentId(parentCommentId)
                .createdTime(createdTime)
                .detail(detail)
                .updateTime(updateTime)
                .build();
    }

    private CommentUpdatedEvent toCommentUpdatedEvent() {
        return new CommentUpdatedEvent(commentId, this.detail, this.updateTime);
    }

    public void delete(LocalDateTime updateTime) {
        this.detail = "";
        this.userId = null;
        this.updateTime = updateTime;
        Events.raise(toCommentDeletedEvent());
    }

    private CommentDeletedEvent toCommentDeletedEvent() {
        return new CommentDeletedEvent(this.commentId, this.updateTime);
    }
}
