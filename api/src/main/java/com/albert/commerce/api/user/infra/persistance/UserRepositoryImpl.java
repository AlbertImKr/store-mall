package com.albert.commerce.api.user.infra.persistance;

import com.albert.commerce.api.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.api.user.command.application.dto.UserProfileRequest;
import com.albert.commerce.api.user.command.domain.QUser;
import com.albert.commerce.api.user.command.domain.User;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.api.user.command.domain.UserRepository;
import com.albert.commerce.api.user.infra.persistance.imports.UserJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final UserJpaRepository userJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public Optional<User> updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        QUser user = QUser.user;
        jpaQueryFactory.update(user)
                .set(user.nickname, userProfileRequest.nickname())
                .set(user.address, userProfileRequest.address())
                .set(user.phoneNumber, userProfileRequest.phoneNumber())
                .set(user.dateOfBirth, userProfileRequest.dateOfBirth())
                .where(user.email.eq(email))
                .execute();
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetchFirst());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        user.updateId(nextId());
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
