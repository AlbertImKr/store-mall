package com.albert.commerce.adapter.in.web.security;

import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.domain.user.Role;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

@Component
public class WithMockOAuth2UserSecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

    public static final String TEST_CLIENT = "my-test-client";
    public static final String NAME_ATTRIBUTE_KEY = "email";
    public static final String ATTRIBUTE_EMAIL_KEY = "email";

    @Autowired
    UserService userService;

    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2User annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(Role.USER.getKey()));

        OAuth2User oAuth2User = new DefaultOAuth2User(authorities,
                Collections.singletonMap(ATTRIBUTE_EMAIL_KEY, annotation.username()), NAME_ATTRIBUTE_KEY);
        Authentication auth = new OAuth2AuthenticationToken(oAuth2User, authorities, TEST_CLIENT);
        context.setAuthentication(auth);

        return context;
    }
}
