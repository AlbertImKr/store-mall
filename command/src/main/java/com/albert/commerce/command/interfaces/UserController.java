package com.albert.commerce.command.interfaces;

import com.albert.commerce.command.application.user.UserService;
import com.albert.commerce.command.application.user.dto.UserProfileRequest;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/users/profile")
    public ResponseEntity<Void> updateUserInfo(Principal principal,
            @RequestBody UserProfileRequest userProfileRequest) {
        String email = principal.getName();
        userService.updateUserInfo(email, userProfileRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public OAuth2User mainPage(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User;
    }
}


