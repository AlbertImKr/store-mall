package com.albert.commerce.user.query.application;

import com.albert.commerce.user.query.domain.UserQueryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserQueryService {

    private final UserQueryDao userDao;

    public UserInfoResponse findByEmail(String email) {
        return UserInfoResponse.from(userDao.findUserProfileByEmail(email));
    }


}
