package com.albert.commerce.application.service.user;

import com.albert.commerce.adapter.in.web.security.CustomOauth2User;
import com.albert.commerce.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    public static final String EMAIL = "email";
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute(EMAIL);
        if (email == null) {
            throw new OAuth2AuthenticationException("Email is not found");
        }
        if (userService.exists(email)) {
            User user = userService.getByEmail(email);
            return new CustomOauth2User(user);
        }
        User user = userService.createByEmail(email);
        return new CustomOauth2User(user);
    }
}
