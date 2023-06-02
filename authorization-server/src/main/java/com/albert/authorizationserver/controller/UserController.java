package com.albert.authorizationserver.controller;


import com.albert.authorizationserver.dto.JoinRequest;
import com.albert.authorizationserver.model.User;
import com.albert.authorizationserver.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity addUser(@Valid JoinRequest joinRequest, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        User user = userService.save(joinRequest);
        userService.login(user);
        return ResponseEntity.created(URI.create("/login")).body("성공적으로 유저를 추가했습니다.");
    }
}
