package com.albert.commerce.infra.command.product.persistence.imports;

import com.albert.commerce.domain.command.product.Product;
import com.albert.commerce.domain.command.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, ProductId> {

}
