package com.albert.commerce.command.application.port.out;

import com.albert.commerce.command.domain.product.Product;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.domain.store.StoreId;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);


    Optional<Product> findByProductId(ProductId productId);

    boolean existsById(ProductId productId);

    Optional<Product> findByStoreIdAndProductId(StoreId storeId, ProductId productId);
}
