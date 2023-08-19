package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.UserDataJpaRepository;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserDao;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    private final UserDataJpaRepository userDataJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userDataJpaRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userDataJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return userDataJpaRepository.findById(userId);
    }
}
