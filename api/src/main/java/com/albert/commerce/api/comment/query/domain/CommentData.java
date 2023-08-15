package com.albert.commerce.api.comment.query.domain;

import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
@Table(name = "comment_query")
@Entity
public class CommentData {

    @AttributeOverride(name = "value", column = @Column(name = "comment_id"))
    @EmbeddedId
    private CommentId commentId;
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    @Embedded
    private UserId userId;
    @Column(name = "nickname")
    private String nickname;
    @AttributeOverride(name = "value", column = @Column(name = "store_id"))
    @Embedded
    private StoreId storeId;
    @Column(name = "store_name")
    private String storeName;
    @AttributeOverride(name = "value", column = @Column(name = "product_id"))
    @Embedded
    private ProductId productId;
    @AttributeOverride(name = "value", column = @Column(name = "parent_comment_id"))
    @Embedded
    private CommentId parentCommentId;
    @Column(name = "prodcut_name")
    private String productName;
    @Column(name = "detail")
    private String detail;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Builder
    private CommentData(CommentId commentId, UserId userId, String nickname, StoreId storeId, String storeName,
            ProductId productId,
            CommentId parentCommentId, String productName, String detail, LocalDateTime createdTime,
            LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.userId = userId;
        this.nickname = nickname;
        this.storeId = storeId;
        this.storeName = storeName;
        this.productId = productId;
        this.parentCommentId = parentCommentId;
        this.productName = productName;
        this.detail = detail;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public StoreId getStoreId() {
        return storeId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public CommentId getParentCommentId() {
        return parentCommentId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void update(String detail, LocalDateTime updatedTime) {
        this.detail = detail;
        this.updatedTime = updatedTime;
    }

    public void delete(LocalDateTime updatedTime) {
        this.detail = null;
        this.userId = null;
        this.updatedTime = updatedTime;
    }
}
