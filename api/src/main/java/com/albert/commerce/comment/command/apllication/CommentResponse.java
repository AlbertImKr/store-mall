package com.albert.commerce.comment.command.apllication;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CommentResponse {

    private CommentId commentId;
    private StoreId storeId;
    private ProductId productId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private String nickname;
    private CommentResponse childComment;
    private String detail;
    private UserId userId;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .storeId(comment.getStoreId())
                .productId(comment.getProductId())
                .nickname(comment.getNickName())
                .createdTime(comment.getCreatedTime())
                .updateTime(comment.getUpdateTime())
                .detail(comment.getDetail())
                .build();
    }
}
