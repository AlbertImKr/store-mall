package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.UserDao;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserDao userDao;

    public User getInfoById(String userId) {
        return userDao.findById(UserId.from(userId))
                .orElseThrow(UserNotFoundException::new);
    }
}
