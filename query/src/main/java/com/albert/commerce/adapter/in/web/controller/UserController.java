package com.albert.commerce.adapter.in.web.controller;

import com.albert.commerce.adapter.in.web.facade.UserFacade;
import com.albert.commerce.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/users/{id}")
    public User getInfoById(@PathVariable String id) {
        return userFacade.getInfoById(id);
    }
}
