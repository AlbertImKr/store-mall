package com.albert.commerce.infra;

import com.albert.commerce.user.security.AuthenticationProviderService;
import com.albert.commerce.user.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final AuthenticationProviderService authenticationProvider;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/users", "/users/joinForm", "/", "/css/**", "/js/**", "/login", "/oauth2/**").permitAll()
                .antMatchers("**").authenticated()
                .and()
                .csrf()
                .ignoringAntMatchers("/users", "/login")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .authenticationProvider(authenticationProvider)
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(tokenRepository())
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return httpSecurity.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}
