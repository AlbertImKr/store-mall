package com.albert.commerce.application.service.comment.dto;

import com.albert.commerce.domain.comment.CommentId;
import java.time.LocalDateTime;

public record CommentDeletedEvent(CommentId commentId, LocalDateTime updatedTime) {

}
