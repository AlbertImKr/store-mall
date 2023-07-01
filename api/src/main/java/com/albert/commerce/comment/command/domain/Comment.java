package com.albert.commerce.comment.command.domain;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @EmbeddedId
    private CommentId commentId;
    @Column(name = "product_id")
    private ProductId productId;
    @Column(name = "store_id")
    private StoreId storeId;
    private String nickName;
    @Column(name = "user_id")
    private UserId userId;

    @OneToOne(mappedBy = "parentComment")
    @JoinColumn(name = "child_comment_id")
    private Comment childComment;
    @OneToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    private String detail;

    @Builder
    private Comment(CommentId commentId, ProductId productId, StoreId storeId, String nickName,
            UserId userId, Comment childComment, Comment parentComment, String detail) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.nickName = nickName;
        this.userId = userId;
        this.childComment = childComment;
        this.parentComment = parentComment;
        this.detail = detail;
    }

    public void addChildComment(Comment comment) {
        this.childComment = comment;
    }
}
