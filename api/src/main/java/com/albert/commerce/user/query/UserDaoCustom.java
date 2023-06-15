package com.albert.commerce.user.query;

import com.albert.commerce.user.application.UserProfileRequest;
import com.albert.commerce.user.command.domain.User;
import java.util.Optional;

public interface UserDaoCustom {

    Optional<User> findUserProfileByEmail(String email);

    Optional<User> updateUserInfo(String email, UserProfileRequest userProfileRequest);
}
