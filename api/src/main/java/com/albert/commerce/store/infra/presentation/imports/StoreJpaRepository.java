package com.albert.commerce.store.infra.presentation.imports;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<Store, StoreId> {

    boolean existsByUserId(UserId userId);

    Optional<Store> findByUserId(UserId userId);

}
