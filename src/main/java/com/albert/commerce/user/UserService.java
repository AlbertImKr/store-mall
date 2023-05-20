package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import com.albert.commerce.user.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(JoinRequest joinRequest) {
        return userRepository.save(joinRequest.toUser(bCryptPasswordEncoder, EncryptionAlgorithm.BCRYPT, Role.USER));
    }

    public void login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(user),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getKey()))
        );

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("이메일 존재하지 않습니다"));
    }
}
