package com.albert.commerce.api.comment.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.Events;
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
    private DomainId commentId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private DomainId productId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    private DomainId storeId;
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    @Embedded
    private DomainId userId;
    @AttributeOverride(name = "value", column = @Column(name = "parent_comment_id"))
    @Embedded
    private DomainId parentCommentId;

    private String detail;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime createdTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updateTime;

    @Builder
    private Comment(DomainId commentId, DomainId productId, DomainId storeId,
            DomainId userId, DomainId parentCommentId, String detail) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
    }

    public void updateId(DomainId commentId, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.commentId = commentId;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        CommentCreatedEvent commentCreatedEvent = CommentCreatedEvent.builder()
                .commentId(commentId)
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .parentCommentId(parentCommentId)
                .createdTime(createdTime)
                .detail(detail)
                .updateTime(updateTime)
                .build();
        Events.raise(commentCreatedEvent);
    }

    public void update(String detail) {
        this.detail = detail;
        this.updateTime = LocalDateTime.now();
    }

    public void delete() {
        this.detail = "";
        this.userId = null;
        this.updateTime = LocalDateTime.now();
    }
}
