package com.albert.commerce.api.product.infra.persistence;

import com.albert.commerce.api.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.api.product.command.domain.Product;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.command.domain.ProductRepository;
import com.albert.commerce.api.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.product.command.domain.QProduct;
import com.albert.commerce.store.command.domain.QStore;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private static JPQLQuery<StoreId> getStoreIdByUserId(UserId userId) {
        QStore qStore = QStore.store;
        return JPAExpressions
                .select(qStore.storeId)
                .from(qStore)
                .where(qStore.userId.eq(
                        userId
                ));
    }


    private final SequenceGenerator sequenceGenerator;
    private final ProductJpaRepository productJpaRepository;


    @Override
    public Product save(Product product) {
        product.updateId(nextId());
        return productJpaRepository.save(product);
    }

    private ProductId nextId() {
        return ProductId.from(sequenceGenerator.generate());
    }

    @Override
    public Optional<Product> findByUserIdAndProductId(UserId userId, ProductId productId) {
        QProduct qProduct = QProduct.product;
        Product product = jpaQueryFactory.select(qProduct)
                .from(qProduct)
                .where(qProduct.productId.eq(productId).and(qProduct.storeId.eq(
                        getStoreIdByUserId(userId)
                )))
                .fetchFirst();
        return Optional.ofNullable(product);
    }

}
