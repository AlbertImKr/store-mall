package com.albert.commerce.domain.query.user;

import com.albert.commerce.domain.command.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserData, UserId> {

    Optional<UserData> findByEmail(String email);
}
