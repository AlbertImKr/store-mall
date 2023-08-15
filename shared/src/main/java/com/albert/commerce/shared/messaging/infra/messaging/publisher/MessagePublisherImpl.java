package com.albert.commerce.shared.messaging.infra.messaging.publisher;

import static com.albert.commerce.shared.config.messaging.command.CommandMessageConfig.COMMAND_CHANNEL;
import static com.albert.commerce.shared.config.messaging.domainevent.DomainEventMessageConfig.DOMAIN_EVENT_CHANNEL;

import com.albert.commerce.shared.messaging.application.Command;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisherImpl implements MessagePublisher {

    private final MessageChannel commandChannel;
    private final MessageChannel domainEventChannel;

    public MessagePublisherImpl(
            @Autowired(required = false) @Qualifier(COMMAND_CHANNEL) MessageChannel commandChannel,
            @Autowired(required = false) @Qualifier(DOMAIN_EVENT_CHANNEL) MessageChannel domainEventChannel
    ) {
        this.commandChannel = commandChannel;
        this.domainEventChannel = domainEventChannel;
    }

    @Override
    public void publish(Command command) {
        commandChannel.send(new GenericMessage<>(command));
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        domainEventChannel.send(new GenericMessage<>(domainEvent));
    }
}
