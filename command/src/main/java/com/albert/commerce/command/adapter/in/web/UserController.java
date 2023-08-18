package com.albert.commerce.command.adapter.in.web;

import com.albert.commerce.shared.messaging.application.CommandGateway;
import com.albert.commerce.shared.messaging.application.UserUpdateCommand;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CommandGateway commandGateway;

    @PutMapping("/users/profile")
    public ResponseEntity<Void> updateUserInfo(Principal principal,
            @RequestBody UserProfileRequest userProfileRequest) {
        String userEmail = principal.getName();
        UserUpdateCommand userUpdateCommand = new UserUpdateCommand(
                userEmail,
                userProfileRequest.nickname(),
                userProfileRequest.dateOfBirth(),
                userProfileRequest.phoneNumber(),
                userProfileRequest.address()
        );
        commandGateway.request(userUpdateCommand);
        return ResponseEntity.ok().build();
    }
}


