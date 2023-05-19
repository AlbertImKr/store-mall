package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    public static final String RIGHT_EMAIL = "jack@email.com";
    public static final String WRONG_EMAIL = "erroremail";
    public static final String RIGHT_8_PASSWORD = "kkkkk!1S";
    public static final String RIGHT_20_PASSWORD = "loooooooooooooong!1S";
    public static final String WRONG_SHORT_7_PASSWORD = "kkkk!1S";
    public static final String WRONG_LONG_21_PASSWORD = "looooooooooooooong!1S";
    public static final String WRONG_NOT_CONTAIN_UPPERCASE_PASSWORD = "kkkkk!1a";
    public static final String WRONG_NOT_CONTAIN_LOWERCASE_PASSWORD = "KKKKK!1A";
    public static final String WRONG_NOT_CONTAIN_SPECIAL_SYMBOLS_PASSWORD = "KKKKKa1A";
    public static final String RIGHT_3_NICKNAME = "kim";
    public static final String RIGHT_10_NICKNAME = "lonickname";
    public static final String WRONG_SHORT_2_NICKNAME = "kk";
    public static final String WRONG_LONG_11_NICKNAME = "loonickname";

    @Autowired
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @DisplayName("누구나 회원 가입 페이지를 접근 가능하다")
    @Test
    void getJoinForm() throws Exception {
        mockMvc.perform(get("/users/joinForm"))
                .andExpect(status().isOk());
    }

    @DisplayName("이메일,닉네임,비밀번호 및 확인 비빌번호를 정확히 입력시 회원이 생성하고 메인 페이지로 리다이렉트 한다")
    @Test
    void addUserSuccess() throws Exception {
        // given,when
        mockMvc.perform(post("/users")
                        .param("email", RIGHT_EMAIL)
                        .param("nickname", RIGHT_3_NICKNAME)
                        .param("password", RIGHT_8_PASSWORD)
                        .param("confirmPassword", RIGHT_8_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // then
        User user = userService.findByEmail(RIGHT_EMAIL);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(user.getEmail()).isEqualTo(RIGHT_EMAIL);
        softAssertions.assertThat(user.getNickname()).isEqualTo(RIGHT_3_NICKNAME);
        EncryptionAlgorithm algorithm = user.getAlgorithm();
        switch (algorithm) {
            case SCRYPT:
                softAssertions.assertThat(new SCryptPasswordEncoder().matches(RIGHT_8_PASSWORD, user.getPassword())).isTrue();
            case BCRYPT:
                softAssertions.assertThat(new BCryptPasswordEncoder().matches(RIGHT_8_PASSWORD, user.getPassword())).isTrue();
        }
        softAssertions.assertAll();
    }

    @DisplayName("이메일,닉네임,비밀번호 및 확인 비빌번호를 잘못 입력시 다시 가입 페이지로 redirect 한다")
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void addUserFailed(String email, String nickname, String password, String confirmPassword, int count) throws Exception {
        // given,when
        mockMvc.perform(post("/users")
                        .param("email", email)
                        .param("nickname", nickname)
                        .param("password", password)
                        .param("confirmPassword", confirmPassword))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(count))
                .andExpect(view().name("user/joinForm"));
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(
                        WRONG_EMAIL, WRONG_SHORT_2_NICKNAME, WRONG_SHORT_7_PASSWORD, WRONG_LONG_21_PASSWORD, 5),
                Arguments.of(
                        WRONG_EMAIL, WRONG_LONG_11_NICKNAME, WRONG_NOT_CONTAIN_LOWERCASE_PASSWORD, WRONG_NOT_CONTAIN_UPPERCASE_PASSWORD, 5),
                Arguments.of(
                        RIGHT_EMAIL, RIGHT_3_NICKNAME, WRONG_NOT_CONTAIN_SPECIAL_SYMBOLS_PASSWORD, RIGHT_8_PASSWORD, 2),
                Arguments.of(RIGHT_EMAIL, RIGHT_10_NICKNAME, RIGHT_8_PASSWORD, RIGHT_20_PASSWORD, 1)
        );
    }


    @DisplayName("로그인 한 사용자는 자신의 profile를 볼수 있다")
    @WithTestUser(username = RIGHT_3_NICKNAME)
    @Test
    void getMyProfileSuccess() throws Exception {
        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 하지 않는 사용자는 profile를 접근할 수 없고 login 페이지로 redirect 한다")
    @Test
    void getMyProfileFailed() throws Exception {
        String testServerUrl = "http://localhost";

        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(testServerUrl + "/login"));
    }


    @DisplayName("이메일로 로그인 성공하면 '/'페이지로 redirect 한다")
    @Test
    void loginByEmail() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                RIGHT_EMAIL,
                RIGHT_3_NICKNAME,
                RIGHT_8_PASSWORD,
                RIGHT_8_PASSWORD);
        userService.save(joinRequest);

        // when,then
        mockMvc.perform(post("/login")
                        .param("username", RIGHT_EMAIL)
                        .param("password", RIGHT_8_PASSWORD))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());
    }
}
