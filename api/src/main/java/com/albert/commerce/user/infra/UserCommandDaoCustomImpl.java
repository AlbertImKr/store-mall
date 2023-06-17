package com.albert.commerce.user.infra;

import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.application.UserProfileRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommandDaoCustomImpl implements UserCommandDaoCustom {

    private final JPAQueryFactory jpaQueryFactory;

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
}
