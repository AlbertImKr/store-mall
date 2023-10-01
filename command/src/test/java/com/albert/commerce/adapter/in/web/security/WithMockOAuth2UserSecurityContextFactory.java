package com.albert.commerce.adapter.in.web.security;

import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

@Component
public class WithMockOAuth2UserSecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

    public static final String TEST_CLIENT = "my-test-client";

    @Autowired
    UserService userService;

    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2User annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        String username = annotation.username();
        OAuth2AuthenticationToken authentication = getOAuth2AuthenticationToken(username, TEST_CLIENT);

        context.setAuthentication(authentication);
        return context;
    }

    private OAuth2AuthenticationToken getOAuth2AuthenticationToken(String email, String registrationId) {
        User user = getUser(email);
        CustomOauth2User customOauth2User = new CustomOauth2User(user);
        return new OAuth2AuthenticationToken(customOauth2User, customOauth2User.getAuthorities(), registrationId);
    }

    private User getUser(String email) {
        if (userService.exists(email)) {
            return userService.getByEmail(email);
        }
        return userService.createByEmail(email);
    }
}
