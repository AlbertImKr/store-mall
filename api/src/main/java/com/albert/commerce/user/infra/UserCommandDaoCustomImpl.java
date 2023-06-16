package com.albert.commerce.user.infra;

import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.application.UserProfileRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommandDaoCustomImpl implements UserCommandDaoCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> updateUserInfo(String email, UserProfileRequest userProfileRequest) {
        QUser user = QUser.user;
        jpaQueryFactory.update(user)
                .set(List.of(user.nickname, user.address, user.phoneNumber, user.dateOfBirth),
                        List.of(userProfileRequest.nickname(), userProfileRequest.address(),
                                userProfileRequest.phoneNumber(), userProfileRequest.dateOfBirth()))
                .where(user.email.eq(email))
                .execute();
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne());
    }
}
