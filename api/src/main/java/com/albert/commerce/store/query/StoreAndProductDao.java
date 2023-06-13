package com.albert.commerce.store.query;

import com.albert.commerce.store.command.domain.StoreId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreAndProductDao extends JpaRepository<StoreAndProduct, StoreId> {

}
