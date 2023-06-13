package com.albert.commerce.user.query;

import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataDao extends JpaRepository<UserProfileResponse, UserId> {

    Optional<UserProfileResponse> findByEmail(String email);

    boolean existsByEmail(String email);
}
