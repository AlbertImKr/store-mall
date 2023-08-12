package com.albert.commerce.api.comment.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;

public class CommentDeletedEvent extends DomainEvent {

    private final DomainId commentId;
    private final LocalDateTime updatedTime;

    public CommentDeletedEvent(DomainId commentId, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.updatedTime = updatedTime;
    }

    public DomainId getCommentId() {
        return commentId;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
