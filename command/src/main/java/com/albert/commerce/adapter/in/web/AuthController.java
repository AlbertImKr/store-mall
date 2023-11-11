package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.security.UserEmail;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.auth.LoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CommandGateway commandGateway;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @UserEmail String email
    ) {
        var loginCommand = toLoginCommand(email);
        commandGateway.request(loginCommand);
        return ResponseEntity.noContent().build();
    }

    private LoginCommand toLoginCommand(String email) {
        return new LoginCommand(email);
    }
}
