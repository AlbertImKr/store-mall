package com.albert.shared.messaging.infra.messaging.publisher;

import com.albert.shared.messaging.application.Command;
import com.albert.shared.messaging.domain.event.DomainEvent;

public interface MessagePublisher {

    void publish(Command command);

    void publish(DomainEvent domainEvent);
}
