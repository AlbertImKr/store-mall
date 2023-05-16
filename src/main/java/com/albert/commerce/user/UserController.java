package com.albert.commerce.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users/joinForm")
    public String viewJoinForm() {
        return "users/joinForm";
    }
}
