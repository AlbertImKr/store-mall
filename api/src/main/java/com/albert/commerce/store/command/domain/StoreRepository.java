package com.albert.commerce.store.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, StoreId> {


}
