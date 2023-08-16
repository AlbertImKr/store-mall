package com.albert.commerce.query.infra.persistance.imports;

import com.albert.commerce.query.domain.user.UserData;
import com.albert.commerce.query.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataJpaRepository extends JpaRepository<UserData, UserId> {

    Optional<UserData> findByEmail(String email);
}
