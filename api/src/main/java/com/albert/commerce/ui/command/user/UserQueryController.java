package com.albert.commerce.ui.command.user;

import com.albert.commerce.application.command.user.dto.UserInfoResponse;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.query.user.UserDao;
import com.albert.commerce.domain.query.user.UserData;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserQueryController {

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
}


