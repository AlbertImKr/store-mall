package com.albert.commerce.comment.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.Column;
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
    @Column(table = "comment", name = "comment_id")
    private CommentId commentId;
    @Column(name = "product_id")
    private ProductId productId;
    @Column(name = "store_id")
    private StoreId storeId;
    @Column(name = "user_id")
    private UserId userId;
    @Column(name = "child_comment_id")
    private String childCommentId;
    @Column(name = "parent_comment_id")
    private String parentCommentId;

    private String detail;

    @Builder
    public Comment(CommentId commentId, ProductId productId, StoreId storeId, String nickname,
            UserId userId, String childCommentId, String parentCommentId, String detail) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
        this.childCommentId = childCommentId;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
    }

    public void updateChildCommentId(CommentId commentId) {
        this.childCommentId = commentId.getValue();
    }
}
