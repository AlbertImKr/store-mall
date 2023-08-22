package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);


    Optional<Product> findByProductId(ProductId productId);

    boolean existsById(ProductId productId);

    Optional<Product> findByStoreIdAndProductId(StoreId storeId, ProductId productId);

    ProductId nextId();
}
