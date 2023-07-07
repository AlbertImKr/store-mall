package com.albert.commerce.product.infra.persistence;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.product.command.domain.QProduct;
import com.albert.commerce.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository, ProductDao {


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

    private Long getProductCount(QProduct qProduct, JPQLQuery<StoreId> storeIdQuery) {
        return jpaQueryFactory
                .select(qProduct.count())
                .from(qProduct)
                .where(
                        qProduct.storeId.eq(
                                storeIdQuery
                        )
                ).fetchOne();
    }

    private final SequenceGenerator sequenceGenerator;
    private final ProductJpaRepository productJpaRepository;


    @Override
    public Product save(Product product) {
        product.updateId(nextId());
        return productJpaRepository.save(product);
    }

    @Override
    public ProductId nextId() {
        return ProductId.from(sequenceGenerator.generate());
    }

    @Override
    public Page<Product> findProductsByUserId(UserId userId, Pageable pageable) {

        QProduct qProduct = QProduct.product;
        JPQLQuery<StoreId> storeIdQuery = getStoreIdByUserId(userId);
        List<Product> contentProducts = jpaQueryFactory.selectFrom(qProduct)
                .where(
                        qProduct.storeId.eq(
                                storeIdQuery
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return PageableExecutionUtils.getPage(contentProducts, pageable,
                () -> getProductCount(qProduct, storeIdQuery));
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

    @Override
    public Optional<Product> findById(ProductId productId) {
        QProduct qProduct = QProduct.product;
        Product product = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.productId.eq(productId))
                .fetchFirst();
        return Optional.ofNullable(product);
    }

    @Override
    public boolean exists(ProductId productId) {
        return productJpaRepository.existsById(productId);
    }

    @Override
    public long getAmount(List<ProductId> productsId) {
        QProduct qProduct = QProduct.product;
        return jpaQueryFactory.select(qProduct.price)
                .from(qProduct)
                .where(qProduct.productId.in(productsId))
                .fetch()
                .stream()
                .map(Money::value)
                .reduce(Long::sum).orElse(0L);
    }

    @Override
    public boolean isValidProductsId(List<ProductId> productsId) {
        QProduct qProduct = QProduct.product;
        return Objects.equals((long) productsId.size(),
                jpaQueryFactory.select(qProduct.productId.count())
                        .from(qProduct)
                        .where(qProduct.productId.in(productsId))
                        .fetchFirst());
    }
}
