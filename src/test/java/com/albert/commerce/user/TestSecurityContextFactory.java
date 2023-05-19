package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class TestSecurityContextFactory implements WithSecurityContextFactory<WithTestUser> {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    public SecurityContext createSecurityContext(WithTestUser withTestUser) {
        String username = withTestUser.username();

        JoinRequest joinRequest = new JoinRequest(
                username + "@email.com",
                username,
                "testPassword",
                "testPassword");
        userService.save(joinRequest);

        // 로그인 username을 email로 설정
        CustomUserDetails principal = customUserDetailsService.loadUserByUsername(username + "@email.com");
        Authentication token =
                new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        return context;
    }
}
