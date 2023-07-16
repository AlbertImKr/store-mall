package com.albert.commerce.user.ui;

import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.command.application.dto.UserProfileRequest;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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


