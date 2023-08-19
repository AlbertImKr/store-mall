package com.albert.commerce.domain.user;

import java.util.Optional;

public interface UserDao {

    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findById(UserId userId);
}
