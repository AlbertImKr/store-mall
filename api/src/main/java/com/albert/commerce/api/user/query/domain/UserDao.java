package com.albert.commerce.api.user.query.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;

public interface UserDao {

    Optional<UserData> findByEmail(String email);

    UserData save(UserData userData);

    Optional<UserData> findById(DomainId userId);
}
