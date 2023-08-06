package com.albert.commerce.infra.command.product.persistence;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.product.QProduct;
import com.albert.commerce.domain.command.store.QStore;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.product.ProductDao;
import com.albert.commerce.domain.query.product.ProductData;
import com.albert.commerce.domain.query.product.QProductData;
import com.albert.commerce.infra.command.product.persistence.imports.ProductDataJpaRepository;
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

    private static JPQLQuery<StoreId> getStoreIdByUserId(UserId userId) {
        QStore qStore = QStore.store;
        return JPAExpressions
                .select(qStore.storeId)
                .from(qStore)
                .where(qStore.userId.eq(
                        userId
                ));
    }

    @Override
    public Optional<ProductData> findById(ProductId productId) {
        QProductData qProduct = QProductData.productData;
        ProductData product = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.productId.eq(productId))
                .fetchFirst();
        return Optional.ofNullable(product);
    }

    @Override
    public boolean exists(ProductId productId) {
        return productDataJpaRepository.existsById(productId);
    }

    @Override
    public long getAmount(List<ProductId> productsId) {
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
    public boolean isValidProductsId(List<ProductId> productsId) {
        QProduct qProduct = QProduct.product;
        return Objects.equals((long) productsId.size(),
                jpaQueryFactory.select(qProduct.productId.count())
                        .from(qProduct)
                        .where(qProduct.productId.in(productsId))
                        .fetchFirst());
    }

    @Override
    public Page<ProductData> findProductsByUserId(UserId userId, Pageable pageable) {

        QProductData qProduct = QProductData.productData;
        JPQLQuery<StoreId> storeIdQuery = getStoreIdByUserId(userId);
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

    private Long getProductCount(QProductData qProduct, JPQLQuery<StoreId> storeIdQuery) {
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
