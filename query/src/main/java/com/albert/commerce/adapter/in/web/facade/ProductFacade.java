package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.ProductDao;
import com.albert.commerce.config.cache.CacheValue;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.exception.error.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductFacade {

    private final ProductDao productDao;

    @Cacheable(value = CacheValue.PRODUCT)
    public Product getById(String id) {
        return productDao.findById(ProductId.from(id))
                .orElseThrow(ProductNotFoundException::new);
    }
}
