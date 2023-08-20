package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.user.UserFacade;
import com.albert.commerce.application.service.user.dto.UserRegisteredEvent;
import com.albert.commerce.application.service.user.dto.UserUpdateEvent;
import com.albert.commerce.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserEventHandler {

    private final UserFacade userFacade;

    @KafkaListener(topics = "UserRegisteredEvent")
    public void handleUserRegisteredEvent(UserRegisteredEvent userRegisteredEvent) {
        User user = User.builder()
                .userId(userRegisteredEvent.userId())
                .nickname(userRegisteredEvent.nickname())
                .email(userRegisteredEvent.email())
                .address(userRegisteredEvent.address())
                .role(userRegisteredEvent.role())
                .phoneNumber(userRegisteredEvent.nickname())
                .dateOfBirth(userRegisteredEvent.dateOfBirth())
                .isActive(userRegisteredEvent.isActive())
                .build();
        userFacade.save(user);
    }

    @KafkaListener(topics = "UserUpdateEvent")
    public void handleUserUpdateEvent(UserUpdateEvent userUpdateEvent) {
        userFacade.update(userUpdateEvent);
    }
}
