package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.user.UserFacade;
import com.albert.commerce.application.service.user.dto.UserRegisteredEvent;
import com.albert.commerce.application.service.user.dto.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDomainEventListener {

    private final UserFacade userFacade;

    @KafkaListener(topics = "UserRegisteredEvent")
    public void handleUserRegisteredEvent(UserRegisteredEvent userRegisteredEvent) {
        userFacade.save(userRegisteredEvent);
    }

    @KafkaListener(topics = "UserUpdatedEvent")
    public void handleUserUpdateEvent(UserUpdatedEvent userUpdatedEvent) {
        userFacade.update(userUpdatedEvent);
    }
}
