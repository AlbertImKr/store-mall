package com.albert.commerce.domain.comment;

import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentUpdatedEvent extends DomainEvent {

    private final CommentId commentId;
    private final String detail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updatedTime;

    public CommentUpdatedEvent(CommentId commentId, String detail, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.detail = detail;
        this.updatedTime = updatedTime;
    }
}
