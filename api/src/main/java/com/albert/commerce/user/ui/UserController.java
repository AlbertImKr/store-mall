package com.albert.commerce.user.ui;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.user.command.application.UserCommandService;
import com.albert.commerce.user.query.application.UserInfoResponse;
import com.albert.commerce.user.query.application.UserProfileRequest;
import com.albert.commerce.user.query.application.UserQueryService;
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

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;


    @GetMapping("/")
    public OAuth2User mainPage(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User;
    }


    @GetMapping("/users/profile")
    public UserInfoResponse getUserInfo(Principal principal) {
        String email = principal.getName();
        UserInfoResponse userInfoResponse = userQueryService.findByEmail(email);
        userInfoResponse.add(
                linkTo(methodOn(UserController.class).updateUserInfo(principal, null))
                        .withSelfRel(),
                BusinessLinks.CREATE_STORE,
                BusinessLinks.GET_STORE,
                BusinessLinks.MY_STORE
        );
        return userInfoResponse;
    }

    @PutMapping("/users/profile")
    public UserInfoResponse updateUserInfo(Principal principal,
            UserProfileRequest userProfileRequest) {
        String email = principal.getName();
        UserInfoResponse userInfoResponse = userCommandService.updateUserInfo(email,
                userProfileRequest);
        userInfoResponse.add(
                linkTo(methodOn(UserController.class).updateUserInfo(principal, null))
                        .withSelfRel(),
                BusinessLinks.CREATE_STORE,
                BusinessLinks.GET_STORE,
                BusinessLinks.MY_STORE
        );
        return userInfoResponse;
    }

}
