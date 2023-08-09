package com.albert.commerce.api.product.infra.persistence.imports;

import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.query.domain.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataJpaRepository extends JpaRepository<ProductData, ProductId> {

}
