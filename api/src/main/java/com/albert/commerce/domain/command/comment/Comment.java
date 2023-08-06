package com.albert.commerce.domain.command.comment;

import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
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
    @AttributeOverride(name = "id", column = @Column(name = "product_id", nullable = false))
    @Embedded
    private ProductId productId;
    @AttributeOverride(name = "id", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = true))
    @Embedded
    private UserId userId;
    @AttributeOverride(name = "id", column = @Column(name = "parent_comment_id", nullable = true))
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
