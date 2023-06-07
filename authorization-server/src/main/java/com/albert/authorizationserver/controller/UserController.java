package com.albert.authorizationserver.controller;


import com.albert.authorizationserver.dto.JoinRequest;
import com.albert.authorizationserver.model.User;
import com.albert.authorizationserver.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(produces = MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")
public class UserController {

    private final UserService userService;
    private final AuthorizationServerSettings authorizationServerSettings;

    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody @Valid JoinRequest joinRequest, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        User user = userService.save(joinRequest);
        userService.login(user);
        URI uri = URI.create(
                authorizationServerSettings.getIssuer() + "/.well-known/openid-configuration");
        return ResponseEntity.created(uri).body("성공적으로 유저를 추가했습니다.");
    }
}
