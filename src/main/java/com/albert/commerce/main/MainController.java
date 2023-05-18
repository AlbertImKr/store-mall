package com.albert.commerce.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String viewMain() {
        return "welcome";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
