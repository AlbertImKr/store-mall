package com.albert.commerce.domain.product;

import com.albert.commerce.domain.store.StoreId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Optional<Product> findById(ProductId productId);

    boolean exists(ProductId productId);

    Product save(Product product);

    Page<Product> findByStoreId(StoreId storeId, Pageable pageable);
}
