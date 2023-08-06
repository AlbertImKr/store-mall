package com.albert.commerce.domain.query.product;

import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.user.UserId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Page<ProductData> findProductsByUserId(UserId userId, Pageable pageable);

    Optional<ProductData> findById(ProductId productId);

    boolean exists(ProductId productId);

    long getAmount(List<ProductId> productsId);

    boolean isValidProductsId(List<ProductId> productsId);
}
