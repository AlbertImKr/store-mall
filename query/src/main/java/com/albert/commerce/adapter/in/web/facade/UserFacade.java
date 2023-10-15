package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.adapter.out.config.cache.CacheValue;
import com.albert.commerce.application.port.out.UserDao;
import com.albert.commerce.application.service.exception.error.UserNotFoundException;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserDao userDao;

    @Cacheable(value = CacheValue.USER)
    public User getInfoById(String userId) {
        return userDao.findById(UserId.from(userId))
                .orElseThrow(UserNotFoundException::new);
    }
}
