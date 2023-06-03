package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/new")
    public String viewJoinForm(@ModelAttribute JoinRequest joinRequest) {
        return "user/sign-in-form";
    }

}
