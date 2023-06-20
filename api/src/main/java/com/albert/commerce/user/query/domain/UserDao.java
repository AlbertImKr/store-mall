package com.albert.commerce.user.query.domain;

import com.albert.commerce.user.query.application.UserInfoResponse;

public interface UserDao {

    UserInfoResponse findUserProfileByEmail(String email);
}
