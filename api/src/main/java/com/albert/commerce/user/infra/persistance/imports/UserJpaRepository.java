package com.albert.commerce.user.infra.persistance.imports;

import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, UserId> {

    boolean existsByEmail(String email);
}
