package com.albert.commerce.api.product.query.domain;

import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Page<ProductData> findProductsByUserId(DomainId userId, Pageable pageable);

    Optional<ProductData> findById(ProductId productId);

    boolean exists(ProductId productId);

    long getAmount(List<ProductId> productsId);

    boolean isValidProductsId(List<ProductId> productsId);
}
