package com.albert.commerce.user.query.domain;

import com.albert.commerce.user.command.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQueryDao {

    User findUserProfileByEmail(String email);
}
