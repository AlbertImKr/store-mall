package com.albert.commerce.api.product.infra.persistence.imports;

import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.common.domain.DomainId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataJpaRepository extends JpaRepository<ProductData, DomainId> {

}
