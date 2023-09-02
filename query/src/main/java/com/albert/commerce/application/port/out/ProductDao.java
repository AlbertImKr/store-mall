package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import java.util.Optional;

public interface ProductDao {

    Optional<Product> findById(ProductId productId);
}
