package com.albert.authorizationserver;


import static com.albert.authorizationserver.model.EncryptionAlgorithm.BCRYPT;
import static com.albert.authorizationserver.model.Role.USER;

import com.albert.authorizationserver.dto.JoinRequest;
import com.albert.authorizationserver.exception.EmailNotFoundException;
import com.albert.authorizationserver.model.User;
import com.albert.authorizationserver.service.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void login(User user) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(user),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole().getKey())));

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("이메일 존재하지 않습니다"));
    }

    public User save(JoinRequest joinRequest) {
//        if (isEmailExists(joinRequest.getEmail())) {
//            throw new EmailAlreadyExistsException("이메일이 이미 존재합니다.");
//        }
        User user = joinRequest.toUser(bCryptPasswordEncoder, BCRYPT, USER);
        return userRepository.save(user);
    }
}
