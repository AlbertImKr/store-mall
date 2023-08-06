package com.albert.commerce.infra.command.product.persistence;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.domain.command.product.Product;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.product.ProductRepository;
import com.albert.commerce.domain.command.product.QProduct;
import com.albert.commerce.domain.command.store.QStore;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.infra.command.product.persistence.imports.ProductJpaRepository;
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
