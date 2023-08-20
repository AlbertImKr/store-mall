package com.albert.commerce.application.service.user;

import com.albert.commerce.application.service.user.dto.UserRegisteredEvent;
import com.albert.commerce.application.service.user.dto.UserUpdatedEvent;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserDao;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserDao userDao;

    @Transactional
    public void save(UserRegisteredEvent userRegisteredEvent) {
        userDao.save(toUser(userRegisteredEvent));
    }

    @Transactional
    public void update(UserUpdatedEvent userUpdatedEvent) {
        User user = userDao.findById(userUpdatedEvent.userId())
                .orElseThrow(UserNotFoundException::new);
        update(userUpdatedEvent, user);
    }

    @Transactional(readOnly = true)
    public User getUserById(UserId userId) {
        return userDao.findById(userId).orElseThrow(UserNotFoundException::new);
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
