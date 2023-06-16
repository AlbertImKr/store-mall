package com.albert.commerce.user.infra;

import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserQueryDao;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQueryDaoImpl implements UserQueryDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findUserProfileByEmail(String email) {
        QUser user = QUser.user;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne());
    }


}
