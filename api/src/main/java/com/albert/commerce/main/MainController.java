package com.albert.commerce.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String viewMain() {
        return "main/welcome";
    }


    @GetMapping("/login")
    public String login() {
        return "main/login";
    }

    @GetMapping(path = "/login", params = "error")
    public String loginFailed(Model model) {
        model.addAttribute("error", "wrong");
        return "main/login";
    }

}
