package com.albert.commerce.user.command.domain;

import com.albert.commerce.user.infra.UserCommandDaoCustom;
import com.albert.commerce.user.infra.UserJpaUserRepository;

public interface UserCommandDAO extends UserJpaUserRepository, UserCommandDaoCustom {


}
