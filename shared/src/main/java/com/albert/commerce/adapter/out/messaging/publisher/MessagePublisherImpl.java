package com.albert.commerce.adapter.out.messaging.publisher;

import static com.albert.commerce.config.messaging.command.CommandMessageConfig.COMMAND_CHANNEL;

import com.albert.commerce.application.port.out.messaging.MessagePublisher;
import com.albert.commerce.application.service.Command;
import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.event.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisherImpl implements MessagePublisher {

    private final MessageChannel commandChannel;
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public MessagePublisherImpl(
            @Autowired(required = false) @Qualifier(COMMAND_CHANNEL) MessageChannel commandChannel,
            KafkaTemplate<String, Message> kafkaTemplate
    ) {
        this.commandChannel = commandChannel;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(Command command) {
        commandChannel.send(new GenericMessage<>(command));
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        kafkaTemplate.send(domainEvent.getClass().getSimpleName(), domainEvent);
    }
}
