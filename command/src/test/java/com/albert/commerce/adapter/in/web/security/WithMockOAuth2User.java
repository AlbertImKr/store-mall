package com.albert.commerce.adapter.in.web.security;

import com.albert.commerce.adapter.in.web.AcceptanceFixture;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOAuth2UserSecurityContextFactory.class)
public @interface WithMockOAuth2User {

    String username() default AcceptanceFixture.TEST_EMAIL;
}
