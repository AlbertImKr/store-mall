package com.albert.commerce.api.product.infra.persistence;

import com.albert.commerce.api.product.command.domain.QProduct;
import com.albert.commerce.api.product.infra.persistence.imports.ProductDataJpaRepository;
import com.albert.commerce.api.product.query.domain.ProductDao;
import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.api.product.query.domain.QProductData;
import com.albert.commerce.api.store.command.domain.QStore;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
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

@RequiredArgsConstructor
@Repository
public class ProductDataDaoImpl implements ProductDao {

    private final ProductDataJpaRepository productDataJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    private static JPQLQuery<DomainId> getStoreIdByUserId(DomainId userId) {
        QStore qStore = QStore.store;
        return JPAExpressions
                .select(qStore.storeId)
                .from(qStore)
                .where(qStore.userId.eq(
                        userId
                ));
    }

    @Override
    public Optional<ProductData> findById(DomainId productId) {
        QProductData qProduct = QProductData.productData;
        ProductData product = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.productId.eq(productId))
                .fetchFirst();
        return Optional.ofNullable(product);
    }

    @Override
    public boolean exists(DomainId productId) {
        return productDataJpaRepository.existsById(productId);
    }

    @Override
    public long getAmount(List<DomainId> productsId) {
        QProductData qProduct = QProductData.productData;
        return jpaQueryFactory.select(qProduct.price)
                .from(qProduct)
                .where(qProduct.productId.in(productsId))
                .fetch()
                .stream()
                .map(Money::value)
                .reduce(Long::sum).orElse(0L);
    }

    @Override
    public boolean isValidProductsId(List<DomainId> productsId) {
        QProduct qProduct = QProduct.product;
        return Objects.equals((long) productsId.size(),
                jpaQueryFactory.select(qProduct.productId.count())
                        .from(qProduct)
                        .where(qProduct.productId.in(productsId))
                        .fetchFirst());
    }

    @Override
    public Page<ProductData> findProductsByUserId(DomainId userId, Pageable pageable) {

        QProductData qProduct = QProductData.productData;
        JPQLQuery<DomainId> storeIdQuery = getStoreIdByUserId(userId);
        List<ProductData> contentProducts = jpaQueryFactory.selectFrom(qProduct)
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
    public ProductData save(ProductData productData) {
        return productDataJpaRepository.save(productData);
    }

    private Long getProductCount(QProductData qProduct, JPQLQuery<DomainId> storeIdQuery) {
        return jpaQueryFactory
                .select(qProduct.count())
                .from(qProduct)
                .where(
                        qProduct.storeId.eq(
                                storeIdQuery
                        )
                ).fetchOne();
    }

}
