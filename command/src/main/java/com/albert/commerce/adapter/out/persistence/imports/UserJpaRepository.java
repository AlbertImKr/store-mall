package com.albert.commerce.adapter.out.persistence.imports;

import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, UserId> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
