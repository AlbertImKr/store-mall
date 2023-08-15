package com.albert.commerce.api.user.query.domain;

import com.albert.commerce.api.user.command.domain.UserId;
import java.util.Optional;

public interface UserDao {

    Optional<UserData> findByEmail(String email);

    UserData save(UserData userData);

    Optional<UserData> findById(UserId userId);
}
