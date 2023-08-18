package com.albert.commerce.query.application.comment.dto;

import com.albert.commerce.query.domain.comment.CommentId;
import java.time.LocalDateTime;

public record CommentDeletedEvent(CommentId commentId, LocalDateTime updatedTime) {

}
