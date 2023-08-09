package com.albert.commerce.api.user.query.domain;

import com.albert.commerce.api.user.command.domain.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserData, UserId> {

    Optional<UserData> findByEmail(String email);
}
