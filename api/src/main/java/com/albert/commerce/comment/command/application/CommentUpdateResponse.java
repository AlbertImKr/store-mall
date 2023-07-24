package com.albert.commerce.comment.command.application;

import com.albert.commerce.comment.command.domain.CommentId;
import lombok.Builder;
import org.springframework.hateoas.RepresentationModel;

public class CommentUpdateResponse extends RepresentationModel<CommentUpdateResponse> {

    private final CommentId commentId;

    @Builder
    public CommentUpdateResponse(CommentId commentId) {
        this.commentId = commentId;
    }

    public static CommentUpdateResponse from(CommentId commentId) {
        return CommentUpdateResponse.builder()
                .commentId(commentId)
                .build();
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
