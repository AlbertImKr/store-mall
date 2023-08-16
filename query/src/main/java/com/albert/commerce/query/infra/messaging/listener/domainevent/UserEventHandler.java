package com.albert.commerce.query.infra.messaging.listener.domainevent;

import com.albert.commerce.query.application.user.UserFacade;
import com.albert.commerce.query.application.user.dto.UserCreatedEvent;
import com.albert.commerce.query.application.user.dto.UserUpdateEvent;
import com.albert.commerce.query.domain.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserEventHandler {

    private final UserFacade userFacade;

    @ServiceActivator(inputChannel = "UserCreatedEvent")
    public void handleUserCreateEvent(UserCreatedEvent userCreatedEvent) {
        UserData userData = UserData.builder()
                .userId(userCreatedEvent.userId())
                .nickname(userCreatedEvent.nickname())
                .email(userCreatedEvent.email())
                .address(userCreatedEvent.address())
                .role(userCreatedEvent.role())
                .phoneNumber(userCreatedEvent.nickname())
                .dateOfBirth(userCreatedEvent.dateOfBirth())
                .isActive(userCreatedEvent.isActive())
                .build();
        userFacade.save(userData);
    }

    @ServiceActivator(inputChannel = "UserUpdateEvent")
    public void handleUserUpdateEvent(UserUpdateEvent userUpdateEvent) {
        userFacade.update(userUpdateEvent);
    }
}
