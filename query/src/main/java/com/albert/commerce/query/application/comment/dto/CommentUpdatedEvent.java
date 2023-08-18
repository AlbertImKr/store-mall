package com.albert.commerce.query.application.comment.dto;

import com.albert.commerce.query.domain.comment.CommentId;
import java.time.LocalDateTime;

public record CommentUpdatedEvent(CommentId commentId, String detail, LocalDateTime updatedTime) {

}
