package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import com.albert.commerce.user.exception.EmailAlreadyExistsException;
import com.albert.commerce.user.exception.EmailNotFoundException;
import com.albert.commerce.user.oauth2.OAuthAttributes;
import com.albert.commerce.user.security.CustomUserDetails;
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
        if (isExistsEmail(joinRequest.getEmail())) {
            throw new EmailAlreadyExistsException("이메일이 이미 존재합니다.");
        }
        User user = joinRequest.toUser(bCryptPasswordEncoder, EncryptionAlgorithm.BCRYPT, Role.USER);
        return userRepository.save(user);
    }

    private boolean isExistsEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
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

    public User oAth2login(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity(bCryptPasswordEncoder, EncryptionAlgorithm.BCRYPT, Role.USER));
        return userRepository.save(user);
    }
}
