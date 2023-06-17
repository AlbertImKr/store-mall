package com.albert.commerce.config;

import com.albert.commerce.user.infra.UserQueryDaoImpl;
import com.albert.commerce.user.query.domain.UserQueryDao;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public UserQueryDao userQueryDao() {
        return new UserQueryDaoImpl(jpaQueryFactory());
    }

}
