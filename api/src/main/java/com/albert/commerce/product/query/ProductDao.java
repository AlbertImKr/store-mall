package com.albert.commerce.product.query;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Page<Product> findProductsByUserEmail(String userEmail, Pageable pageable);

    Product findByUserEmailAndProductId(String name, ProductId productId);
}
