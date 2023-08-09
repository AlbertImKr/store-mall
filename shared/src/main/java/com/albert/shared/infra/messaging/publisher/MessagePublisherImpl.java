package com.albert.shared.infra.messaging.publisher;

import com.albert.shared.application.Command;
import com.albert.shared.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessagePublisherImpl implements MessagePublisher {

    private final MessageChannel commandChannel;
    private final MessageChannel internalDomainEventChannel;

    @Override
    public void publish(Command command) {
        commandChannel.send(new GenericMessage<>(command));
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        internalDomainEventChannel.send(new GenericMessage<>(domainEvent));
    }
}