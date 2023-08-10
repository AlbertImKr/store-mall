package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.common.domain.DomainId;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

public class CommentUpdateResponse extends RepresentationModel<CommentUpdateResponse> {

    private final DomainId commentId;

    @Builder
    public CommentUpdateResponse(DomainId commentId) {
        this.commentId = commentId;
    }

    public static CommentUpdateResponse from(DomainId commentId) {
        return CommentUpdateResponse.builder()
                .commentId(commentId)
                .build();
    }

    public DomainId getCommentId() {
        return commentId;
    }
}
