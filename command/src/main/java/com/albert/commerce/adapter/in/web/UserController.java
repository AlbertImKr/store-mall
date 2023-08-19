package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.dto.UserUpdateRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.UserUpdateCommand;
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
        var userUpdateCommand = new UserUpdateCommand(
                userEmail,
                userUpdateRequest.nickname(),
                userUpdateRequest.dateOfBirth(),
                userUpdateRequest.phoneNumber(),
                userUpdateRequest.address()
        );
        boolean success = commandGateway.request(userUpdateCommand);
        if (!success) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}


