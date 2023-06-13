package com.albert.commerce.user.ui;

import com.albert.commerce.user.application.UserService;
import com.albert.commerce.user.query.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/")
    public OAuth2User mainPage(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User;
    }


    @GetMapping("/users/profile")
    public UserProfileResponse getUserInfo(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getName();
        return userService.findByEmail(email);
    }

}
