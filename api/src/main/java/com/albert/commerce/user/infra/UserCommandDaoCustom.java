package com.albert.commerce.user.infra;

import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.application.UserProfileRequest;
import java.util.Optional;

public interface UserCommandDaoCustom {

    Optional<User> updateUserInfo(String email, UserProfileRequest userProfileRequest);
}
