package com.albert.commerce.api.user.infra.persistance;

import com.albert.commerce.api.user.command.domain.User;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.api.user.command.domain.UserRepository;
import com.albert.commerce.api.user.infra.persistance.imports.UserJpaRepository;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        user.updateId(nextId(), LocalDateTime.now(), LocalDateTime.now());
        return userJpaRepository.save(user);
    }

    private UserId nextId() {
        return UserId.from(sequenceGenerator.generate());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
