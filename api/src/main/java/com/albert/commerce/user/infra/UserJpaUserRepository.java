package com.albert.commerce.user.infra;

import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaUserRepository extends JpaRepository<User, UserId> {

    boolean existsByEmail(String email);
}
