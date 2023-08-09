package com.albert.shared.messaging.application;

import com.albert.shared.messaging.domain.event.DomainEvent;
import com.albert.shared.messaging.domain.event.Events;
import com.albert.shared.messaging.infra.messaging.publisher.MessagePublisher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RequiredArgsConstructor
@Aspect
@Component
public class CommandHandlerAspect {

    private final MessagePublisher messagePublisher;

    @Before("execution(* com.albert.commerce.api.*.command.application.*Service.*(..))"
            + "&& @annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransactionalCommandExecution() {
        if (!isEventTransactionSynchronizationRegistered()) {
            var eventTransactionSynchronization = new EventTransactionSynchronization(messagePublisher);
            TransactionSynchronizationManager.registerSynchronization(eventTransactionSynchronization);
        }
    }

    private boolean isEventTransactionSynchronizationRegistered() {
        List<TransactionSynchronization> synchronizations = TransactionSynchronizationManager.getSynchronizations();
        return synchronizations.stream()
                .anyMatch(EventTransactionSynchronization.class::isInstance);
    }

    private record EventTransactionSynchronization(MessagePublisher messagePublisher) implements
            TransactionSynchronization {

        @Override
        public void afterCommit() {
            List<DomainEvent> events = Events.getEvents();
            for (DomainEvent event : events) {
                messagePublisher.publish(event);
            }
            Events.clear();
        }

        @Override
        public void afterCompletion(int status) {
            Events.clear();
        }
    }
}
