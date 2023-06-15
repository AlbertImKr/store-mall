package com.albert.commerce.user.query;

import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, UserId>, UserDaoCustom {

    boolean existsByEmail(String email);
}
