package com.albert.commerce.query.infra.messaging.listener.domainevent;

import com.albert.commerce.query.application.comment.CommentFacade;
import com.albert.commerce.query.application.comment.dto.CommentCreatedEvent;
import com.albert.commerce.query.application.comment.dto.CommentDeletedEvent;
import com.albert.commerce.query.application.comment.dto.CommentUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentEventHandler {

    private final CommentFacade commentFacade;

    @KafkaListener(topics = "CommentCreatedEvent")
    public void handleCommentCreatedEvent(CommentCreatedEvent commentCreatedEvent) {
        commentFacade.create(commentCreatedEvent);
    }

    @KafkaListener(topics = "CommentUpdatedEvent")
    public void handleCommentUpdatedEvent(CommentUpdatedEvent commentUpdatedEvent) {
        commentFacade.update(commentUpdatedEvent);
    }

    @KafkaListener(topics = "CommentDeletedEvent")
    public void handleCommentDeletedEvent(CommentDeletedEvent commentDeletedEvent) {
        commentFacade.delete(commentDeletedEvent);
    }
}
