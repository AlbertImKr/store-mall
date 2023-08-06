package com.albert.commerce.domain.command.user;

import com.albert.commerce.application.command.user.dto.UserProfileRequest;
import java.util.Optional;

public interface UserRepository {

    Optional<User> updateUserInfo(String email, UserProfileRequest userProfileRequest);

    boolean existsByEmail(String email);

    User save(User user);
}
