package com.albert.commerce.command.infra.presentation.imports;

import com.albert.commerce.command.domain.store.Store;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<Store, StoreId> {

    boolean existsByUserId(UserId userId);

    Optional<Store> findByUserId(UserId userId);
}
