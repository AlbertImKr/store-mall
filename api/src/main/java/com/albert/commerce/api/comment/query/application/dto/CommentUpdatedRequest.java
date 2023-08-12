package com.albert.commerce.api.comment.query.application.dto;

import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;

public record CommentUpdatedRequest(DomainId commentId, String detail, LocalDateTime updatedTime) {

}
