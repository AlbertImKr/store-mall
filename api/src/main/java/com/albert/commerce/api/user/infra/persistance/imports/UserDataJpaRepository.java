package com.albert.commerce.api.user.infra.persistance.imports;

import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataJpaRepository extends JpaRepository<UserData, DomainId> {

    Optional<UserData> findByEmail(String email);
}
