package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @DisplayName("joinRequest를 받고 userRepostiory를 통해 User를 저장한다")
    @Test
    void saveUser() {
        // given
        JoinRequest joinRequest = mock(JoinRequest.class);
        User user = mock(User.class);
        given(joinRequest.toUser()).willReturn(user);

        // when
        userService.save(joinRequest);

        // then
        verify(userRepository).save(user);
    }

}
