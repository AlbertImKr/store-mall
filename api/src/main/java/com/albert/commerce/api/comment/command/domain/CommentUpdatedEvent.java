package com.albert.commerce.api.comment.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;

public class CommentUpdatedEvent extends DomainEvent {

    private final DomainId commentId;
    private final String detail;
    private final LocalDateTime updatedTime;

    public CommentUpdatedEvent(DomainId commentId, String detail, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.detail = detail;
        this.updatedTime = updatedTime;
    }

    public DomainId getCommentId() {
        return commentId;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
