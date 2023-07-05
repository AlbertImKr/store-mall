package com.albert.commerce.user.infra.persistance;

import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User findUserByEmail(String email) {
        QUser qUser = QUser.user;
        User user = jpaQueryFactory.selectFrom(qUser)
                .where(qUser.email.eq(email))
                .fetchOne();
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
