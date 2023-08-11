package com.albert.commerce.api.user.infra.persistance;

import com.albert.commerce.api.user.infra.persistance.imports.UserDataJpaRepository;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
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
    public Optional<UserData> findById(DomainId userId) {
        return userDataJpaRepository.findById(userId);
    }
}
