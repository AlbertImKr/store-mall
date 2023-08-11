package com.albert.commerce.api.user.query.application;

import com.albert.commerce.api.user.query.application.dto.UserUpdateRequest;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.exception.UserNotFoundException;
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
    public void update(UserUpdateRequest userUpdateRequest) {
        UserData userData = userDao.findById(userUpdateRequest.getUserId())
                .orElseThrow(UserNotFoundException::new);
        userData.update(
                userUpdateRequest.getDateOfBirth(),
                userUpdateRequest.getAddress(),
                userUpdateRequest.getNickname(),
                userUpdateRequest.getPhoneNumber()
        );
    }
}
