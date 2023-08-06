package com.albert.commerce.infra.command.product.persistence.imports;

import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.query.product.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataJpaRepository extends JpaRepository<ProductData, ProductId> {

}
