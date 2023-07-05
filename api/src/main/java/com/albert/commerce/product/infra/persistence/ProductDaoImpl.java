package com.albert.commerce.product.infra.persistence;

import com.albert.commerce.product.UnauthorizedModificationException;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.QProduct;
import com.albert.commerce.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao {

    private final JPAQueryFactory jpaQueryFactory;
    private final ProductJpaRepository productJpaRepository;

    private static JPQLQuery<UserId> getUserIdByEmail(String userEmail) {
        QUser qUser = QUser.user;
        return JPAExpressions
                .select(qUser.id)
                .from(qUser)
                .where(qUser.email.eq(userEmail));
    }

    private static JPQLQuery<StoreId> getStoreIdByUserId(JPQLQuery<UserId> userIdByEmail) {
        QStore qStore = QStore.store;
        return JPAExpressions
                .select(qStore.storeId)
                .from(qStore)
                .where(qStore.userId.eq(
                        userIdByEmail
                ));
    }

    @Override
    public Page<Product> findProductsByUserEmail(String userEmail, Pageable pageable) {

        QProduct qProduct = QProduct.product;
        JPQLQuery<StoreId> storeIdQuery = getStoreIdByUserId(getUserIdByEmail(userEmail));
        List<Product> contentProducts = jpaQueryFactory.selectFrom(qProduct)
                .where(
                        qProduct.storeId.eq(
                                storeIdQuery
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> productJPAQuery = jpaQueryFactory
                .select(qProduct.count())
                .from(qProduct)
                .where(
                        qProduct.storeId.eq(
                                storeIdQuery
                        )
                );
        return PageableExecutionUtils.getPage(contentProducts, pageable, productJPAQuery::fetchOne);
    }

    @Override
    public Product findByUserEmailAndProductId(String userEmail, ProductId productId) {
        QProduct qProduct = QProduct.product;
        Product product = jpaQueryFactory.select(qProduct)
                .from(qProduct)
                .where(qProduct.productId.eq(productId).and(qProduct.storeId.eq(
                        getStoreIdByUserId(getUserIdByEmail(userEmail))
                )))
                .fetchFirst();
        if (product == null) {
            throw new UnauthorizedModificationException();
        }
        return product;
    }

    @Override
    public Product findById(ProductId productId) {
        QProduct qProduct = QProduct.product;
        Product product = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.productId.eq(productId))
                .fetchFirst();
        if (product == null) {
            throw new ProductNotFoundException();
        }
        return product;
    }

    @Override
    public List<Product> findProductsByProductsId(List<ProductId> productsId, StoreId storeId) {
        QProduct qProduct = QProduct.product;
        return productsId.stream()
                .map(productId ->
                        jpaQueryFactory.select(qProduct)
                                .from(qProduct)
                                .where(qProduct.productId.eq(productId)
                                        .and(qProduct.storeId.eq(storeId)))
                                .fetchFirst()
                )
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(ProductId productId) {
        return productJpaRepository.existsById(productId);
    }
}
