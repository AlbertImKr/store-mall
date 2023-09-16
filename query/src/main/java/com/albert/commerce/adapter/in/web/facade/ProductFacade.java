package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.ProductDao;
import com.albert.commerce.config.cache.CacheConfig;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.exception.error.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductFacade implements CacheConfig {

    private final ProductDao productDao;

    @Cacheable(value = "product", key = "#id")
    public Product getById(String id) {
        return productDao.findById(ProductId.from(id))
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public String getCacheName() {
        return "product";
    }

    @Override
    public long getTtl() {
        return 3600;
    }
}
