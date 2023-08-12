package com.albert.commerce.api.comment.infra.messaging.listener.domainevent;

import com.albert.commerce.api.comment.command.domain.CommentCreatedEvent;
import com.albert.commerce.api.comment.command.domain.CommentUpdatedEvent;
import com.albert.commerce.api.comment.query.application.CommentFacade;
import com.albert.commerce.api.comment.query.application.dto.CommentCreatedRequest;
import com.albert.commerce.api.comment.query.application.dto.CommentUpdatedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentEventHandler {

    private final CommentFacade commentFacade;

    @ServiceActivator(inputChannel = "com.albert.commerce.api.comment.command.domain.CommentCreatedEvent")
    public void handleCommentCreatedEvent(CommentCreatedEvent commentCreatedEvent) {
        CommentCreatedRequest commentCreatedRequest = toCommentCreatedRequest(commentCreatedEvent);
        commentFacade.create(commentCreatedRequest);
    }

    @ServiceActivator(inputChannel = "com.albert.commerce.api.comment.command.domain.CommentUpdatedEvent")
    public void handleCommentUpdatedEvent(CommentUpdatedEvent commentUpdatedEvent) {
        CommentUpdatedRequest commentCreatedRequest = toCommentCreatedRequest(commentUpdatedEvent);
        commentFacade.update(commentCreatedRequest);
    }

    private static CommentCreatedRequest toCommentCreatedRequest(CommentCreatedEvent commentCreatedEvent) {
        return CommentCreatedRequest.builder()
                .commentId(commentCreatedEvent.getCommentId())
                .parentCommentId(commentCreatedEvent.getParentCommentId())
                .userId(commentCreatedEvent.getUserId())
                .productId(commentCreatedEvent.getProductId())
                .storeId(commentCreatedEvent.getStoreId())
                .detail(commentCreatedEvent.getDetail())
                .createdTime(commentCreatedEvent.getCreatedTime())
                .updateTime(commentCreatedEvent.getUpdateTime())
                .build();
    }

    private static CommentUpdatedRequest toCommentCreatedRequest(CommentUpdatedEvent commentUpdatedEvent) {
        return new CommentUpdatedRequest(commentUpdatedEvent.getCommentId(), commentUpdatedEvent.getDetail(),
                commentUpdatedEvent.getUpdatedTime());
    }
}
