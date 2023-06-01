package com.albert.commerce.infra;

import com.albert.commerce.user.security.AuthenticationProviderService;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final AuthenticationProviderService authenticationProvider;

//    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        requestMatcherRegistry -> requestMatcherRegistry
                                .requestMatchers("/users", "/users/new", "/", "/css/**", "/js/**",
                                        "/login", "/oauth2/**").permitAll()
                                .requestMatchers("**").authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .tokenRepository(tokenRepository())
                        .userDetailsService(userDetailsService));
        return httpSecurity.build();
    }

//    @Bean
//    public ClientRegistrationRepository inMemoryClientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(
//                ClientRegistration.withRegistrationId("inMemory")
//                        .clientId("test")
//                        .clientSecret("password")
//                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                        .scope(OidcScopes.PROFILE, OidcScopes.OPENID)
//                        .build()
//        );
//    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


}
