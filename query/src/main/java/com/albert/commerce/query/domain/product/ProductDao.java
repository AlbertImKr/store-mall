package com.albert.commerce.query.domain.product;

import com.albert.commerce.query.domain.store.StoreId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Optional<ProductData> findById(ProductId productId);

    boolean exists(ProductId productId);

    ProductData save(ProductData productData);

    Page<ProductData> findByStoreId(StoreId storeId, Pageable pageable);
}
