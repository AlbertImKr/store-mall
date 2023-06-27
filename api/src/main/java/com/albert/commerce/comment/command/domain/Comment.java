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
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @EmbeddedId
    private CommentId commentId;
    @Column(nullable = false)
    private ProductId productId;
    @Column(nullable = false)
    private StoreId storeId;
    @Column(nullable = false)
    private UserId userId;

    @Builder
    private Comment(CommentId commentId, ProductId productId, StoreId storeId, UserId userId) {
        this.commentId = commentId;
        this.productId = productId;
        this.storeId = storeId;
        this.userId = userId;
    }
}
