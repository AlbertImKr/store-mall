package com.albert.commerce.user.query.domain;

import com.albert.commerce.user.command.domain.User;

public interface UserDao {

    User findUserProfileByEmail(String email);

}
