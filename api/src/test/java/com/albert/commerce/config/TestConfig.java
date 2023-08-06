package com.albert.commerce.config;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.domain.command.user.UserRepository;
import com.albert.commerce.infra.command.user.persistance.UserRepositoryImpl;
import com.albert.commerce.infra.command.user.persistance.imports.UserJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class TestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl(jpaQueryFactory(), userJpaRepository, sequenceGenerator);
    }

}
