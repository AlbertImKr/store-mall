package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class CommentResponse {

    private DomainId commentId;
    private DomainId storeId;
    private DomainId productId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private String nickname;
    private DomainId parentCommentId;
    private String detail;
    private DomainId userId;

    @Builder
    private CommentResponse(DomainId commentId, DomainId storeId, DomainId productId,
            LocalDateTime createdTime, LocalDateTime updateTime, String nickname,
            DomainId parentCommentId,
            String detail, DomainId userId) {
        this.commentId = commentId;
        this.storeId = storeId;
        this.productId = productId;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.nickname = nickname;
        this.parentCommentId = parentCommentId;
        this.detail = detail;
        this.userId = userId;
    }
}
