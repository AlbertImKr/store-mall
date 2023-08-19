package com.albert.commerce.adapter.out.persistance.imports;

import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataJpaRepository extends JpaRepository<User, UserId> {

    Optional<User> findByEmail(String email);
}
