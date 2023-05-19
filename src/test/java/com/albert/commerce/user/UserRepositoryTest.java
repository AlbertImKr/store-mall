package com.albert.commerce.user;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    public static final String RIGHT_EMAIL = "jack@email.com";
    public static final String RIGHT_8_PASSWORD = "kkkkk!1S";
    public static final String RIGHT_3_NICKNAME = "kim";

    @Autowired
    private UserRepository userRepository;

    @DisplayName("새로운 유저를 저장한다")
    @Test
    void saveUser() {
        // given
        User user = User.builder()
                .nickname(RIGHT_3_NICKNAME)
                .email(RIGHT_EMAIL)
                .password(RIGHT_8_PASSWORD)
                .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(savedUser.getNickname()).isEqualTo(RIGHT_3_NICKNAME);
                    softly.assertThat(savedUser.getPassword()).isEqualTo(RIGHT_8_PASSWORD);
                    softly.assertThat(savedUser.getEmail()).isEqualTo(RIGHT_EMAIL);
                }
        );
    }
}
