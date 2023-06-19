package com.albert.commerce.user.command.domain;

import com.albert.commerce.user.command.application.UserProfileRequest;
import java.util.Optional;

public interface UserRepository {

    Optional<User> updateUserInfo(String email, UserProfileRequest userProfileRequest);

    boolean existsByEmail(String email);

    User save(User user);
}
