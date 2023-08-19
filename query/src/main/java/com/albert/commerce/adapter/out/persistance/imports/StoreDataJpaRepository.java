package com.albert.commerce.adapter.out.persistance.imports;

import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDataJpaRepository extends JpaRepository<Store, StoreId> {

    Optional<Store> findByUserId(UserId userId);
}
