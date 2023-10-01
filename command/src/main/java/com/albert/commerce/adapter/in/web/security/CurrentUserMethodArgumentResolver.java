package com.albert.commerce.adapter.in.web.security;

import com.albert.commerce.application.service.exception.error.UnauthorizedUserException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String EMAIL = "email";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(String.class) &&
                parameter.hasParameterAnnotation(UserEmail.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !isOAuth2UserPrincipal(authentication)) {
            throw new UnauthorizedUserException();
        }
        String email = ((OAuth2User) authentication.getPrincipal()).getAttribute(EMAIL);
        if (email == null) {
            throw new UnauthorizedUserException();
        }
        return email;
    }

    private static boolean isOAuth2UserPrincipal(Authentication authentication) {
        return OAuth2User.class.isAssignableFrom(authentication.getPrincipal().getClass());
    }
}

