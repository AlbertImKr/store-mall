package com.albert.commerce.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class MainController {

    @GetMapping("/")
    public String viewMain() {
        return "welcome";
    }

    @GetMapping("/index")
    public String index() {
        return "welcome";
    }

}
