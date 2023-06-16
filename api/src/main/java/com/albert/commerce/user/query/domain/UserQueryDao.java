package com.albert.commerce.user.query.domain;

import com.albert.commerce.user.command.domain.User;
import java.util.Optional;


public interface UserQueryDao {

    Optional<User> findUserProfileByEmail(String email);
}
