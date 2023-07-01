package com.albert.commerce.store.infra.presentation;

import com.albert.commerce.store.command.application.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.infra.presentation.imports.StoreJpaRepository;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreCommandDaoCustomImpl implements StoreRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final StoreJpaRepository storeJpaRepository;

    private static JPQLQuery<UserId> getUserIdJPQLQuery(String email) {
        QUser qUser = QUser.user;
        return JPAExpressions.select(qUser.id)
                .from(qUser)
                .where(qUser.email.eq(email))
                .limit(1);
    }

    @Override
    public Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest,
            String email) {
        QStore store = QStore.store;
        jpaQueryFactory
                .update(store)
                .set(store.storeName, updateStoreRequest.storeName())
                .set(store.address, updateStoreRequest.address())
                .set(store.email, updateStoreRequest.email())
                .set(store.ownerName, updateStoreRequest.ownerName())
                .set(store.phoneNumber, updateStoreRequest.phoneNumber())
                .where(store.userId.eq(
                        getUserIdJPQLQuery(email)
                ))
                .execute();

        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(store)
                .where(store.userId.eq(getUserIdJPQLQuery(email)))
                .fetchFirst());
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return storeJpaRepository.existsByUserId(userId);
    }

    @Override
    public Store save(Store store) {
        return storeJpaRepository.save(store);
    }
}
