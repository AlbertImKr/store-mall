package com.albert.commerce.application.service;

import com.albert.commerce.application.port.out.messaging.MessagePublisher;
import com.albert.commerce.domain.event.Events;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Profile("messaging")
@RequiredArgsConstructor
@Aspect
@Component
public class CommandHandlerAspect {

    private final MessagePublisher messagePublisher;

    @Before("execution(* com.albert.commerce.application.service.*.*Service.*(..))"
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
            Events.getEvents()
                    .forEach(messagePublisher::publish);
        }

        @Override
        public void afterCompletion(int status) {
            Events.clear();
        }
    }
}
