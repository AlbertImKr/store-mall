package com.albert.commerce.infra.command.store.presentation.imports;

import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.store.StoreData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDataJpaRepository extends JpaRepository<StoreData, StoreId> {

    Optional<StoreData> findByUserId(UserId userId);

}
