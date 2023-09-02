package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.UserJpaRepository;
import com.albert.commerce.application.port.out.UserDao;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId);
    }
}
