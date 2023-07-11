package com.albert.commerce.user.ui;

import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.command.application.dto.UserProfileRequest;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDao userDao;


    @GetMapping("/")
    public OAuth2User mainPage(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User;
    }


    @GetMapping("/users/profile")
    public UserInfoResponse getUserInfo(Principal principal) {
        UserData user = userDao.findByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);
        return UserInfoResponse.from(user);
    }

    @PutMapping("/users/profile")
    public ResponseEntity updateUserInfo(Principal principal,
            @Valid @RequestBody UserProfileRequest userProfileRequest, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        String email = principal.getName();
        UserInfoResponse userInfoResponse = userService.updateUserInfo(email,
                userProfileRequest);
        return ResponseEntity.ok().body(userInfoResponse);
    }
}


