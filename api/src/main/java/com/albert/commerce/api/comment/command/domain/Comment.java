package com.albert.commerce.api.comment.command.domain;

import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.common.domain.DomainId;
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
    @AttributeOverride(name = "id", column = @Column(name = "comment_id", nullable = false))
    private CommentId commentId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "store_id", nullable = false))
    private DomainId storeId;
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    @Embedded
    private DomainId userId;
    @AttributeOverride(name = "id", column = @Column(name = "parent_comment_id"))
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
    private Comment(CommentId commentId, ProductId productId, DomainId storeId,
            DomainId userId, CommentId parentCommentId, String detail) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
    }

    public void updateId(CommentId commentId) {
        this.commentId = commentId;
        this.createdTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
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
