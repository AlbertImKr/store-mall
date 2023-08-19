package com.albert.commerce.application.service.user;

import com.albert.commerce.application.service.user.dto.UserUpdateEvent;
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
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional
    public void update(UserUpdateEvent userUpdateEvent) {
        User user = userDao.findById(userUpdateEvent.userId())
                .orElseThrow(UserNotFoundException::new);
        user.update(
                userUpdateEvent.dateOfBirth(),
                userUpdateEvent.address(),
                userUpdateEvent.nickname(),
                userUpdateEvent.phoneNumber()
        );
    }

    @Transactional(readOnly = true)
    public User getUserById(UserId userId) {
        return userDao.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
