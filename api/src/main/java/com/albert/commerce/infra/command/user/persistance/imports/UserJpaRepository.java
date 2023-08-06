package com.albert.commerce.infra.command.user.persistance.imports;

import com.albert.commerce.domain.command.user.User;
import com.albert.commerce.domain.command.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, UserId> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
