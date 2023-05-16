package com.albert.commerce.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/users/joinForm")
    public String viewJoinForm() {
        return "users/joinForm";
    }

    @PostMapping("/users")
    public String addUser(String email, String nickname, String password, String confirmPassword) {
        return "redirect:/";
    }
}
