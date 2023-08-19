package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.user.User;
import java.util.Optional;

public interface UserRepository {

    boolean existsByEmail(String email);

    User save(User user);

    Optional<User> findByEmail(String email);
}
