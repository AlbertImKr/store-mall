package com.albert.authorizationserver.controller;


import com.albert.authorizationserver.dto.JoinRequest;
import com.albert.authorizationserver.model.User;
import com.albert.authorizationserver.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity addUser(@Valid JoinRequest joinRequest, BindingResult bindingResult) {
        User user = userService.save(joinRequest);
        userService.login(user);
        return ResponseEntity.ok().body(user.getEmail() + "가입 성공했습니다.");
    }
}
