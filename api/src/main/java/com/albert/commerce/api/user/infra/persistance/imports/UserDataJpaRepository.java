package com.albert.commerce.api.user.infra.persistance.imports;

import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.api.user.query.domain.UserData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataJpaRepository extends JpaRepository<UserData, UserId> {

    Optional<UserData> findByEmail(String email);
}
