package com.albert.commerce.domain.comment;

import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class CommentDeletedEvent extends DomainEvent {

    private final CommentId commentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updatedTime;

    public CommentDeletedEvent(CommentId commentId, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.updatedTime = updatedTime;
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
