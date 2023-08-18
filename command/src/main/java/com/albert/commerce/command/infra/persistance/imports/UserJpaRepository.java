package com.albert.commerce.command.infra.persistance.imports;

import com.albert.commerce.command.domain.user.User;
import com.albert.commerce.command.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, UserId> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
