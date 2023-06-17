package com.albert.commerce.store.command.domain;

import com.albert.commerce.store.infra.StoreCommandDaoCustom;
import com.albert.commerce.store.infra.StoreJpaRepository;

public interface StoreRepository extends StoreJpaRepository, StoreCommandDaoCustom {

}
