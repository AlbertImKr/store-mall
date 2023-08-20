package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.comment.CommentFacade;
import com.albert.commerce.application.service.comment.dto.CommentDeletedEvent;
import com.albert.commerce.application.service.comment.dto.CommentPostedCommand;
import com.albert.commerce.application.service.comment.dto.CommentUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentEventHandler {

    private final CommentFacade commentFacade;

    @KafkaListener(topics = "CommentPostedCommand")
    public void handleCommentPostedCommand(CommentPostedCommand commentPostedCommand) {
        commentFacade.post(commentPostedCommand);
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
