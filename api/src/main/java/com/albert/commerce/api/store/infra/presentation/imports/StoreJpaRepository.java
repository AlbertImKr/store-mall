package com.albert.commerce.api.store.infra.presentation.imports;

import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<Store, DomainId> {

    boolean existsByUserId(UserId userId);

    Optional<Store> findByUserId(UserId userId);
}
