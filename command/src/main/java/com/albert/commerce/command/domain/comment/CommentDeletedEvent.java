package com.albert.commerce.command.domain.comment;

import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;

public class CommentDeletedEvent extends DomainEvent {

    private final CommentId commentId;
    private final LocalDateTime updatedTime;

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
