package com.albert.commerce.adapter.in.web.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomOAuth2AccountSecurityContextFactory.class)
public @interface WithMockCustomOAuth2Account {

    String username() default "username";

    String email() default "test@email.com";

    String role() default "ROLE_USER";

    String registrationId() default "test-client";

}
