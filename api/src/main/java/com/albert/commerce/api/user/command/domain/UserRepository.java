package com.albert.commerce.api.user.command.domain;

import com.albert.commerce.api.user.command.application.dto.UserProfileRequest;
import java.util.Optional;

public interface UserRepository {

    Optional<User> updateUserInfo(String email, UserProfileRequest userProfileRequest);

    boolean existsByEmail(String email);

    User save(User user);
}
