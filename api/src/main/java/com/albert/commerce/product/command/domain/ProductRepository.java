package com.albert.commerce.product.command.domain;

import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    ProductId nextId();

    Optional<Product> findByUserIdAndProductId(UserId userId, ProductId productId);
}
