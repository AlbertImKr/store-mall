package com.albert.commerce.api.comment.query.application.dto;

import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;

public record CommentDeletedRequest(DomainId commentId, LocalDateTime updatedTime) {

}
