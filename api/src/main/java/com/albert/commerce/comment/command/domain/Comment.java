package com.albert.commerce.comment.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "comment_id", nullable = false))
    private CommentId commentId;
    @AttributeOverride(name = "id", column = @Column(name = "product_id", nullable = false))
    @Embedded
    private ProductId productId;
    @AttributeOverride(name = "id", column = @Column(name = "store_id", nullable = false))
    @Embedded
    private StoreId storeId;
    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
    @Embedded
    private UserId userId;
    @AttributeOverride(name = "id", column = @Column(name = "parent_comment_id", nullable = true))
    @Embedded
    private CommentId parentCommentId;

    private String detail;

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
    }
}
