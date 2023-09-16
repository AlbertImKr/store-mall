package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.StoreDao;
import com.albert.commerce.config.cache.CacheValue;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.exception.error.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreFacade {

    private final StoreDao storeDao;

    @Cacheable(value = CacheValue.STORE)
    public Store getById(String id) {
        return storeDao.findById(StoreId.from(id))
                .orElseThrow(StoreNotFoundException::new);
    }
}
