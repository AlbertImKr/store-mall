package com.albert.commerce.api.product.query.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Page<ProductData> findProductsByUserId(DomainId userId, Pageable pageable);

    Optional<ProductData> findById(DomainId productId);

    boolean exists(DomainId productId);

    long getAmount(List<DomainId> productsId);

    boolean isValidProductsId(List<DomainId> productsId);

    ProductData save(ProductData productData);
}
