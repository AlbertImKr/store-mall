package com.albert.commerce.application.port.out.messaging;

import com.albert.commerce.application.service.Command;
import com.albert.commerce.domain.event.DomainEvent;

public interface MessagePublisher {

    void publish(Command command);

    void publish(DomainEvent domainEvent);
}
