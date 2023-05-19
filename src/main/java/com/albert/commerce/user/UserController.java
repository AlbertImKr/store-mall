package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import com.albert.commerce.user.dto.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/joinForm")
    public String viewJoinForm(@ModelAttribute JoinRequest joinRequest) {
        return "user/joinForm";
    }

    @PostMapping("/users")
    public String addUser(JoinRequest joinRequest) {
        User user = userService.save(joinRequest);
        userService.login(user);
        return "redirect:/";
    }

    @GetMapping("/users/profile")
    public String viewProfile(@CurrentUser User user, Model model) {
        ProfileResponse profileResponse = ProfileResponse.from(user);
        model.addAttribute(profileResponse);
        return "user/profile";
    }

}
