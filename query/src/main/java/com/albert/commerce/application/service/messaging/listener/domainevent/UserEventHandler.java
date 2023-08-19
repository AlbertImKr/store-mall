package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.user.UserFacade;
import com.albert.commerce.application.service.user.dto.UserCreatedEvent;
import com.albert.commerce.application.service.user.dto.UserUpdateEvent;
import com.albert.commerce.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserEventHandler {

    private final UserFacade userFacade;

    @KafkaListener(topics = "UserCreatedEvent")
    public void handleUserCreateEvent(UserCreatedEvent userCreatedEvent) {
        User user = User.builder()
                .userId(userCreatedEvent.userId())
                .nickname(userCreatedEvent.nickname())
                .email(userCreatedEvent.email())
                .address(userCreatedEvent.address())
                .role(userCreatedEvent.role())
                .phoneNumber(userCreatedEvent.nickname())
                .dateOfBirth(userCreatedEvent.dateOfBirth())
                .isActive(userCreatedEvent.isActive())
                .build();
        userFacade.save(user);
    }

    @KafkaListener(topics = "UserUpdateEvent")
    public void handleUserUpdateEvent(UserUpdateEvent userUpdateEvent) {
        userFacade.update(userUpdateEvent);
    }
}
