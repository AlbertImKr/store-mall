package com.albert.commerce.product.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {

    boolean deleteProductByProductId(ProductId productId);
}
