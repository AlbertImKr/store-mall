package com.albert.commerce.user.query.application;

import static com.albert.commerce.common.units.BusinessLinks.USER_INFO_RESPONSE_LINKS;

import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserDao userDao;

    @Transactional(readOnly = true)
    public UserInfoResponse findByEmail(String email) {
        User user = userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return UserInfoResponse.from(user).add(USER_INFO_RESPONSE_LINKS);
    }
}
