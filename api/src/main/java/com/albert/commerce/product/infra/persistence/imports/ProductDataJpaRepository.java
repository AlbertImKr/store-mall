package com.albert.commerce.product.infra.persistence.imports;

import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.domain.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataJpaRepository extends JpaRepository<ProductData, ProductId> {

}
