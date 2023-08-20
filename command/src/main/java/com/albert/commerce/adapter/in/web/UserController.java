package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.dto.UserUploadRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.UserUploadCommand;
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
    public ResponseEntity<Void> upload(
            Principal principal,
            @RequestBody UserUploadRequest userUploadRequest
    ) {
        String userEmail = principal.getName();
        var userUploadCommand = toUserUploadCommand(userUploadRequest, userEmail);
        commandGateway.request(userUploadCommand);
        return ResponseEntity.ok().build();
    }

    private static UserUploadCommand toUserUploadCommand(UserUploadRequest userUploadRequest, String userEmail) {
        return new UserUploadCommand(
                userEmail,
                userUploadRequest.nickname(),
                userUploadRequest.dateOfBirth(),
                userUploadRequest.phoneNumber(),
                userUploadRequest.address()
        );
    }
}


