package com.albert.commerce.api.user.query.application;

import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserDao userDao;


    public void save(UserData userData) {
        userDao.save(userData);
    }
}
