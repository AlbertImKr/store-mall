package com.albert.commerce.api.product.command.domain;

import com.albert.commerce.api.user.command.domain.UserId;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByUserIdAndProductId(UserId userId, ProductId productId);
}
