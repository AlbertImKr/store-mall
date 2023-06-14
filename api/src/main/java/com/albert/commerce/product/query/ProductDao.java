package com.albert.commerce.product.query;

import com.albert.commerce.product.application.ProductResponse;
import com.albert.commerce.product.application.ProductsResponse;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.QProduct;
import com.albert.commerce.product.ui.ProductController;
import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductDao {

    private final JPAQueryFactory jpaQueryFactory;

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
                .where(qStore.storeUserId.userId.eq(
                        userIdByEmail
                ));
    }

    public ProductsResponse findProductsByUserEmail(String userEmail) {

        QProduct qProduct = QProduct.product;

        List<Product> products = jpaQueryFactory.selectFrom(qProduct)
                .where(
                        qProduct.storeId.eq(
                                getStoreIdByUserId(getUserIdByEmail(userEmail))
                        )
                ).fetch();
        return ProductsResponse.from(products.stream()
                .map(product -> ProductResponse.from(product)
                        .add(WebMvcLinkBuilder.linkTo(ProductController.class)
                                .slash(product.getProductId().getId())
                                .withSelfRel()))
                .collect(Collectors.toList()));
    }
}
