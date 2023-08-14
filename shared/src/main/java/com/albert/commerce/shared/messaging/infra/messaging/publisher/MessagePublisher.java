package com.albert.commerce.shared.messaging.infra.messaging.publisher;

import com.albert.commerce.shared.messaging.application.Command;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;

public interface MessagePublisher {

    void publish(Command command);

    void publish(DomainEvent domainEvent);
}
