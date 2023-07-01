package com.albert.commerce.comment.infra.presentation;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDTO {

    private CommentId commentId;
    private ProductId productId;
    private CommentId parentCommentId;
    private UserId userId;
    private String nickName;
    private StoreId storeId;
    private String detail;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private CommentDTO childCommentDTO;

    public static CommentDTO from(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .productId(comment.getProductId())
                .userId(comment.getUserId())
                .nickName(comment.getNickName())
                .storeId(comment.getStoreId())
                .detail(comment.getDetail())
                .parentCommentId(comment.getChildComment() == null ? null
                        : comment.getChildComment().getCommentId())
                .build();
    }


    public Comment toEntity() {
        return Comment.builder()
                .commentId(commentId)
                .productId(productId)
                .userId(userId)
                .nickName(nickName)
                .storeId(storeId)
                .detail(detail)
                .build();
    }

    public void updateChildCommentDTO(CommentDTO commentDTO) {
        this.childCommentDTO = commentDTO;
    }
}
