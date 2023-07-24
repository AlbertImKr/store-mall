package com.albert.commerce.user.query.domain;

import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserData, UserId> {

    Optional<UserData> findByEmail(String email);
}
