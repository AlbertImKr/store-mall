package com.albert.commerce.comment.query;

import com.albert.commerce.comment.command.apllication.CommentResponse;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Getter
@Setter
@Relation(value = "comment", collectionRelation = "comments")
public class CommentDTO {

    private CommentId commentId;
    private UserId userId;
    private String nickname;
    private StoreId storeId;
    private ProductId productId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private String childCommentId;
    private String parentCommentId;
    private String detail;
    private CommentDTO childComment;

    public static CommentDTO from(CommentResponse commentResponse) {
        return CommentDTO.builder()
                .commentId(commentResponse.getCommentId())
                .storeId(commentResponse.getStoreId())
                .productId(commentResponse.getProductId())
                .createdTime(commentResponse.getCreatedTime())
                .updateTime(commentResponse.getUpdateTime())
                .nickname(commentResponse.getNickname())
                .childCommentId(commentResponse.getChildCommentId())
                .parentCommentId(commentResponse.getParentCommentId())
                .detail(commentResponse.getDetail())
                .userId(commentResponse.getUserId())
                .build();
    }
}
