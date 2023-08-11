package com.albert.commerce.api.comment.infra.messaging.listener.domainevent;

import com.albert.commerce.api.comment.command.domain.CommentCreatedEvent;
import com.albert.commerce.api.comment.query.application.CommentFacade;
import com.albert.commerce.api.comment.query.application.dto.CommentCreatedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentEventHandler {

    private final CommentFacade commentFacade;

    @ServiceActivator(inputChannel = "com.albert.commerce.api.comment.command.domain.CommentCreatedEvent")
    public void handleProductCreatedEvent(CommentCreatedEvent commentCreatedEvent) {
        CommentCreatedRequest commentCreatedRequest = CommentCreatedRequest.builder()
                .commentId(commentCreatedEvent.getCommentId())
                .parentCommentId(commentCreatedEvent.getParentCommentId())
                .userId(commentCreatedEvent.getUserId())
                .productId(commentCreatedEvent.getProductId())
                .storeId(commentCreatedEvent.getStoreId())
                .detail(commentCreatedEvent.getDetail())
                .createdTime(commentCreatedEvent.getCreatedTime())
                .updateTime(commentCreatedEvent.getUpdateTime())
                .build();
        commentFacade.create(commentCreatedRequest);
    }
}
