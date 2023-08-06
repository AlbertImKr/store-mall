package com.albert.commerce.infra.command.user.persistance.imports;

import com.albert.commerce.domain.command.user.User;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.user.UserData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataJpaRepository extends JpaRepository<UserData, UserId> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
