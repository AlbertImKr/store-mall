package com.albert.shared.infra.messaging.publisher;

import com.albert.shared.application.Command;
import com.albert.shared.domain.event.DomainEvent;

public interface MessagePublisher {

    void publish(Command command);

    void publish(DomainEvent domainEvent);
}
