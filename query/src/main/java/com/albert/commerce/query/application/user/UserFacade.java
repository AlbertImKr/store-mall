package com.albert.commerce.query.application.user;

import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.query.application.user.dto.UserUpdateEvent;
import com.albert.commerce.query.domain.user.UserDao;
import com.albert.commerce.query.domain.user.UserData;
import com.albert.commerce.query.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserDao userDao;

    @Transactional
    public void save(UserData userData) {
        userDao.save(userData);
    }

    @Transactional
    public void update(UserUpdateEvent userUpdateEvent) {
        UserData userData = userDao.findById(userUpdateEvent.userId())
                .orElseThrow(UserNotFoundException::new);
        userData.update(
                userUpdateEvent.dateOfBirth(),
                userUpdateEvent.address(),
                userUpdateEvent.nickname(),
                userUpdateEvent.phoneNumber()
        );
    }

    @Transactional(readOnly = true)
    public UserData getUserById(UserId userId) {
        return userDao.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public UserData getUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
