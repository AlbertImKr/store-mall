package com.albert.commerce.api.comment.query.application.dto;

import com.albert.commerce.api.comment.command.domain.CommentId;
import java.time.LocalDateTime;

public record CommentUpdatedRequest(CommentId commentId, String detail, LocalDateTime updatedTime) {

}
