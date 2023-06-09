package com.albert.commerce.store.query;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDao extends JpaRepository<Store, StoreId> {

    boolean existsByStoreUserId(StoreUserId storeUserId);

    Optional<Store> findByStoreUserId(StoreUserId storeUserId);
}
