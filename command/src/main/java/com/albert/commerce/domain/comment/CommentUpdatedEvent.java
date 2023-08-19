package com.albert.commerce.domain.comment;

import com.albert.commerce.domain.event.DomainEvent;
import java.time.LocalDateTime;

public class CommentUpdatedEvent extends DomainEvent {

    private final CommentId commentId;
    private final String detail;
    private final LocalDateTime updatedTime;

    public CommentUpdatedEvent(CommentId commentId, String detail, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.detail = detail;
        this.updatedTime = updatedTime;
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}