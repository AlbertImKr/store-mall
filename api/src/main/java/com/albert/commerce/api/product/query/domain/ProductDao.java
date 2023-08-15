package com.albert.commerce.api.product.query.domain;

import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.store.command.domain.StoreId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Optional<ProductData> findById(ProductId productId);

    boolean exists(ProductId productId);

    ProductData save(ProductData productData);

    Page<ProductData> findByStoreId(StoreId storeId, Pageable pageable);
}
