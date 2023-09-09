package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.UserDao;
import com.albert.commerce.config.cache.CacheConfig;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade implements CacheConfig {

    private final UserDao userDao;

    @Cacheable(value = "user", key = "#userId")
    public User getInfoById(String userId) {
        return userDao.findById(UserId.from(userId))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public String getCacheName() {
        return "user";
    }

    @Override
    public long getTtl() {
        return 3600;
    }
}
