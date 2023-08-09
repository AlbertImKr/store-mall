package com.albert.commerce.api.user.ui;

import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.command.application.dto.UserProfileRequest;
import com.albert.commerce.api.user.command.domain.UserId;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/users/profile")
    public ResponseEntity<Map<String, String>> updateUserInfo(Principal principal,
            @RequestBody UserProfileRequest userProfileRequest) {
        String email = principal.getName();
        UserId userId = userService.updateUserInfo(email, userProfileRequest);
        return ResponseEntity.ok().body(Map.of("userId", userId.getId()));
    }
}


