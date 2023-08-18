package com.albert.commerce.api.product.infra.persistence.imports;

import com.albert.commerce.api.product.command.domain.Product;
import com.albert.commerce.api.product.command.domain.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, ProductId> {

}
