package com.albert.commerce.command.adapter.in.web;

import com.albert.commerce.command.adapter.in.web.dto.UserUpdateRequest;
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
    public ResponseEntity<Void> update(
            Principal principal,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        String userEmail = principal.getName();
        UserUpdateCommand userUpdateCommand = new UserUpdateCommand(
                userEmail,
                userUpdateRequest.nickname(),
                userUpdateRequest.dateOfBirth(),
                userUpdateRequest.phoneNumber(),
                userUpdateRequest.address()
        );
        commandGateway.request(userUpdateCommand);
        return ResponseEntity.ok().build();
    }
}


