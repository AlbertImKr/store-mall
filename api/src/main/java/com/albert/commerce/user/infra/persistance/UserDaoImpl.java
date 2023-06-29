package com.albert.commerce.user.infra.persistance;

import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.query.application.UserInfoResponse;
import com.albert.commerce.user.query.domain.UserDao;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserInfoResponse findUserProfileByEmail(String email) {
        QUser user = QUser.user;
        User targetUser = jpaQueryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne();
        if (targetUser == null) {
            throw new UserNotFoundException();
        }
        return UserInfoResponse.from(targetUser);
    }

    @Override
    public UserId findUserIdByEmail(String email) {
        QUser user = QUser.user;
        UserId userId = jpaQueryFactory
                .select(user.id)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne();
        if (userId == null) {
            throw new UserNotFoundException();
        }
        return userId;
    }
}
