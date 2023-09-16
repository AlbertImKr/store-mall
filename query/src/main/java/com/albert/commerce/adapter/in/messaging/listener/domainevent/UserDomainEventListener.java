package com.albert.commerce.adapter.in.messaging.listener.domainevent;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.UserRegisteredEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.UserUpdatedEvent;
import com.albert.commerce.adapter.out.persistence.imports.UserJpaRepository;
import com.albert.commerce.config.cache.CacheValue;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.exception.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDomainEventListener {

    private final UserJpaRepository userJpaRepository;

    @KafkaListener(topics = "UserRegisteredEvent")
    public void handleUserRegisteredEvent(UserRegisteredEvent userRegisteredEvent) {
        userJpaRepository.save(toUser(userRegisteredEvent));
    }

    @Transactional
    @CacheEvict(value = CacheValue.USER, key = "#userUpdatedEvent.userId().value")
    @KafkaListener(topics = "UserUpdatedEvent")
    public void handleUserUpdateEvent(UserUpdatedEvent userUpdatedEvent) {
        User user = userJpaRepository.findById(userUpdatedEvent.userId())
                .orElseThrow(UserNotFoundException::new);
        update(userUpdatedEvent, user);
    }

    private static User toUser(UserRegisteredEvent userRegisteredEvent) {
        return User.builder()
                .userId(userRegisteredEvent.userId())
                .nickname(userRegisteredEvent.nickname())
                .email(userRegisteredEvent.email())
                .address(userRegisteredEvent.address())
                .role(userRegisteredEvent.role())
                .phoneNumber(userRegisteredEvent.nickname())
                .dateOfBirth(userRegisteredEvent.dateOfBirth())
                .isActive(userRegisteredEvent.isActive())
                .createdTime(userRegisteredEvent.createdTime())
                .updatedTime(userRegisteredEvent.updatedTime())
                .build();
    }

    private static void update(UserUpdatedEvent userUpdatedEvent, User user) {
        user.update(
                userUpdatedEvent.dateOfBirth(),
                userUpdatedEvent.address(),
                userUpdatedEvent.nickname(),
                userUpdatedEvent.phoneNumber(),
                userUpdatedEvent.updatedTime()
        );
    }
}
