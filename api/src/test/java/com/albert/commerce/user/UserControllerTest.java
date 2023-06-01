package com.albert.commerce.user;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.albert.commerce.user.dto.JoinRequest;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
    public static final String OTHER_EMAIL = "other@email.com";

    @Autowired
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @DisplayName("회원 가입")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class SignInTest {

        @DisplayName("누구나 회원 가입 페이지를 접근할 수 있다")
        @Test
        void getJoinForm() throws Exception {
            mockMvc.perform(get("/users/new"))
                    .andExpect(status().isOk());
        }

        @DisplayName("가입 요청시 info가 정확히 입력하면 회원이 생성하고 메인 페이지로 리다이렉트 한다")
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
                    softAssertions.assertThat(new SCryptPasswordEncoder().matches(RIGHT_8_PASSWORD,
                            user.getPassword())).isTrue();
                case BCRYPT:
                    softAssertions.assertThat(new BCryptPasswordEncoder().matches(RIGHT_8_PASSWORD,
                            user.getPassword())).isTrue();
            }
            softAssertions.assertAll();
        }


        @DisplayName("가입 요청시 info가 정확히 입력하면 회원이 생성하고 메인 페이지로 리다이렉트 한다")
        @Test
        void addUserFailedBy() throws Exception {
            // given
            userService.save(new JoinRequest(RIGHT_EMAIL, RIGHT_3_NICKNAME, RIGHT_8_PASSWORD,
                    RIGHT_8_PASSWORD));

            // when, then
            mockMvc.perform(post("/users")
                            .param("email", RIGHT_EMAIL)
                            .param("nickname", RIGHT_3_NICKNAME)
                            .param("password", RIGHT_8_PASSWORD)
                            .param("confirmPassword", RIGHT_8_PASSWORD))
                    .andExpect(status().isForbidden())
                    .andExpect(redirectedUrlPattern("/users/new**"));
        }

        @DisplayName("가입 요청시 이메일,닉네임,비밀번호 및 확인비빌번호를 잘못 입력하면 다시 가입 페이지로 redirect 한다")
        @ParameterizedTest
        @MethodSource("provideStringsForIsBlank")
        void addUserFailedByAlreadyExistsEmail(String email, String nickname, String password,
                String confirmPassword,
                int count) throws Exception {
            // given,when
            mockMvc.perform(post("/users")
                            .param("email", email)
                            .param("nickname", nickname)
                            .param("password", password)
                            .param("confirmPassword", confirmPassword))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(model().errorCount(count))
                    .andExpect(view().name("user/sign-in-form"));
        }

        private Stream<Arguments> provideStringsForIsBlank() {
            return Stream.of(
                    Arguments.of(
                            WRONG_EMAIL, WRONG_SHORT_2_NICKNAME, WRONG_SHORT_7_PASSWORD,
                            WRONG_LONG_21_PASSWORD, 5),
                    Arguments.of(
                            WRONG_EMAIL, WRONG_LONG_11_NICKNAME,
                            WRONG_NOT_CONTAIN_LOWERCASE_PASSWORD,
                            WRONG_NOT_CONTAIN_UPPERCASE_PASSWORD, 5),
                    Arguments.of(
                            RIGHT_EMAIL, RIGHT_3_NICKNAME,
                            WRONG_NOT_CONTAIN_SPECIAL_SYMBOLS_PASSWORD, RIGHT_8_PASSWORD, 2),
                    Arguments.of(RIGHT_EMAIL, RIGHT_10_NICKNAME, RIGHT_8_PASSWORD,
                            RIGHT_20_PASSWORD, 1)
            );
        }

    }

    @DisplayName("로그인")
    @Nested
    class LoginTest {

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
        void loginByEmailSuccess() throws Exception {
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


        @DisplayName("로그인 시 비번이 일치하지 않으면 '/login?error' 페이지로 redirect 한다")
        @Test
        void loginByEmailFailedByPassword() throws Exception {
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
                            .param("password", WRONG_LONG_11_NICKNAME))
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }

        @DisplayName("로그인 시 이메일이 존재하지 않으면 '/login?error' 페이지로 redirect 한다")
        @Test
        void loginByEmailFailedByEmail() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", OTHER_EMAIL)
                            .param("password", RIGHT_8_PASSWORD))
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }

        @DisplayName("로그인 시 이메일 형식 맞지 않으면 DB에 접근하지 않고 '/login?error' 페이지로 redirect 한다")
        @Test
        void loginByEmailFailedByEmailType() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", WRONG_EMAIL)
                            .param("password", RIGHT_8_PASSWORD))
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }

        @DisplayName("로그인 시 비번 형식 맞지 않으면 DB에 접근하지 않고 '/login?error' 페이지로 redirect 한다")
        @Test
        void loginByEmailFailedByPasswordType() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", RIGHT_EMAIL)
                            .param("password", WRONG_LONG_11_NICKNAME))
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }
    }
}
