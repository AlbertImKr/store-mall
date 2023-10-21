package com.albert.commerce.application.service.auth;

import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.units.CommandChannelNames;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    @Transactional
    @ServiceActivator(inputChannel = CommandChannelNames.LOGIN_CHANNEL)
    public Success login(LoginCommand loginCommand) {
        String email = loginCommand.getEmail();
        if (!userService.exists(email)) {
            userService.createByEmail(email);
        }
        return Success.getInstance();
    }
}
