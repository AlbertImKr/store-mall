package com.albert.commerce.api.user.query.domain;

import java.util.Optional;

public interface UserDao {

    Optional<UserData> findByEmail(String email);
}
