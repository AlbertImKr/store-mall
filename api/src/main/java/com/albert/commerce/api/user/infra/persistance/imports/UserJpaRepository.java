package com.albert.commerce.api.user.infra.persistance.imports;

import com.albert.commerce.api.user.command.domain.User;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, DomainId> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
