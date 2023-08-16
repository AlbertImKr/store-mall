package com.albert.commerce.query.domain.user;

import java.util.Optional;

public interface UserDao {

    Optional<UserData> findByEmail(String email);

    UserData save(UserData userData);

    Optional<UserData> findById(UserId userId);
}
