package com.albert.commerce.adapter.in.web.user;

import com.albert.commerce.adapter.in.web.security.UserEmail;
import com.albert.commerce.adapter.in.web.user.request.UserUploadRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.user.UserUploadCommand;
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
            @UserEmail String email,
            @RequestBody UserUploadRequest userUploadRequest
    ) {
        var userUploadCommand = toUserUploadCommand(userUploadRequest, email);
        commandGateway.request(userUploadCommand);
        return ResponseEntity.noContent().build();
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


