package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.adapter.out.config.cache.CacheValue;
import com.albert.commerce.application.port.out.StoreDao;
import com.albert.commerce.application.service.exception.error.StoreNotFoundException;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
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
