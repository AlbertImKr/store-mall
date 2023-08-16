package com.albert.commerce.query.infra.persistance;

import com.albert.commerce.query.domain.user.UserDao;
import com.albert.commerce.query.domain.user.UserData;
import com.albert.commerce.query.domain.user.UserId;
import com.albert.commerce.query.infra.persistance.imports.UserDataJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    private final UserDataJpaRepository userDataJpaRepository;

    @Override
    public Optional<UserData> findByEmail(String email) {
        return userDataJpaRepository.findByEmail(email);
    }

    @Override
    public UserData save(UserData userData) {
        return userDataJpaRepository.save(userData);
    }

    @Override
    public Optional<UserData> findById(UserId userId) {
        return userDataJpaRepository.findById(userId);
    }
}
