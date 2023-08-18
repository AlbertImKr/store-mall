package com.albert.commerce.query.infra.persistance.imports;

import com.albert.commerce.query.domain.store.StoreData;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDataJpaRepository extends JpaRepository<StoreData, StoreId> {

    Optional<StoreData> findByUserId(UserId userId);
}
