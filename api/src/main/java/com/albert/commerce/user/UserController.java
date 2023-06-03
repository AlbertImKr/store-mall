package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import com.albert.commerce.user.dto.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(value = "/users/new", params = "error")
    public String viewJoinForm(@ModelAttribute JoinRequest joinRequest, Model model) {
        model.addAttribute("error");
        return "user/sign-in-form";
    }

    @GetMapping("/users/profile")
    public String viewProfile(@CurrentUser User user, Model model) {
        ProfileResponse profileResponse = ProfileResponse.from(user);
        model.addAttribute(profileResponse);
        return "user/profile";
    }

}
