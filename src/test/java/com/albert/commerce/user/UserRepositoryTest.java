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

    public static final long TEST_ID = 100L;
    public static final String TEST_NICKNAME = "jack";
    public static final String TEST_EMAIL = "jack@email.com";
    public static final String TEST_PASSWORD = "testPassword";

    @Autowired
    private UserRepository userRepository;

    @DisplayName("새로운 유저를 저장한다")
    @Test
    void saveUser() {
        // given
        User user = User.builder()
                .id(TEST_ID)
                .nickname(TEST_NICKNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(savedUser.getId()).isEqualTo(TEST_ID);
                    softly.assertThat(savedUser.getNickname()).isEqualTo(TEST_NICKNAME);
                    softly.assertThat(savedUser.getPassword()).isEqualTo(TEST_PASSWORD);
                    softly.assertThat(savedUser.getEmail()).isEqualTo(TEST_EMAIL);
                }
        );
    }
}
