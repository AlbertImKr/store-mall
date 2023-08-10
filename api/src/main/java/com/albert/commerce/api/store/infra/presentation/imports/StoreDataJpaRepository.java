package com.albert.commerce.api.store.infra.presentation.imports;

import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDataJpaRepository extends JpaRepository<StoreData, DomainId> {

    Optional<StoreData> findByUserId(UserId userId);
}
