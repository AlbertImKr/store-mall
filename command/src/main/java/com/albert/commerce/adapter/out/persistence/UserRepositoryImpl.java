package com.albert.commerce.adapter.out.persistence;

import com.albert.commerce.adapter.out.persistence.imports.UserJpaRepository;
import com.albert.commerce.application.port.out.UserRepository;
import com.albert.commerce.application.port.out.persistence.SequenceGenerator;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
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
        return userJpaRepository.save(user);
    }

    @Override
    public UserId nextId() {
        return UserId.from(sequenceGenerator.generate());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
