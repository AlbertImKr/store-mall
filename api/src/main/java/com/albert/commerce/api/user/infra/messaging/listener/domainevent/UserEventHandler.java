package com.albert.commerce.api.user.infra.messaging.listener.domainevent;

import com.albert.commerce.api.user.command.domain.UserCreatedEvent;
import com.albert.commerce.api.user.query.application.UserFacade;
import com.albert.commerce.api.user.query.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserEventHandler {

    private final UserFacade userFacade;

    @ServiceActivator(inputChannel = "com.albert.commerce.api.user.command.domain.UserCreatedEvent")
    public void handleUserCreateEvent(UserCreatedEvent userCreatedEvent) {
        UserData userData = UserData.builder()
                .userId(userCreatedEvent.getUserId())
                .nickname(userCreatedEvent.getNickname())
                .email(userCreatedEvent.getEmail())
                .address(userCreatedEvent.getAddress())
                .role(userCreatedEvent.getRole())
                .phoneNumber(userCreatedEvent.getPhoneNumber())
                .dateOfBirth(userCreatedEvent.getDateOfBirth())
                .isActive(userCreatedEvent.isActive())
                .build();
        userFacade.save(userData);
    }
}
