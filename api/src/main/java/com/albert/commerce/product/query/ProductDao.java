package com.albert.commerce.product.query;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Page<Product> findProductsByUserId(UserId userId, Pageable pageable);

    Optional<Product> findById(ProductId productId);

    boolean exists(ProductId productId);

    long getAmount(List<ProductId> productsId);

    boolean isValidProductsId(List<ProductId> productsId);
}
