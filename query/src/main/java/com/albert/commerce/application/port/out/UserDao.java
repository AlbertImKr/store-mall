package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(UserId userId);
}
