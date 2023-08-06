package com.albert.commerce.domain.command.product;

import com.albert.commerce.domain.command.user.UserId;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByUserIdAndProductId(UserId userId, ProductId productId);
}
