package com.albert.commerce.user.infra;

import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserQueryDao;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQueryDaoImpl implements UserQueryDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User findUserProfileByEmail(String email) {
        QUser user = QUser.user;
        User targetUser = jpaQueryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne();
        if (targetUser == null) {
            throw new UserNotFoundException();
        }
        return targetUser;
    }


}
