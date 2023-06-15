package com.albert.commerce.user.ui;

import com.albert.commerce.user.application.UserProfileRequest;
import com.albert.commerce.user.application.UserService;
import com.albert.commerce.user.query.UserInfoResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public UserInfoResponse getUserInfo(Principal principal) {
        String email = principal.getName();
        return userService.findByEmail(email);
    }

    @PutMapping("/users/profile")
    public UserInfoResponse updateUserInfo(Principal principal,
            UserProfileRequest userProfileRequest) {
        String email = principal.getName();
        return userService.updateUserInfo(email, userProfileRequest);
    }

}
