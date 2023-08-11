package com.albert.commerce.api.product.command.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByUserIdAndProductId(DomainId userId, DomainId productId);

    Optional<Product> findByProductId(DomainId productId);
}
