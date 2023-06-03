package com.albert.commerce.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        .requestMatchers("/index").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
