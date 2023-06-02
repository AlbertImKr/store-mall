package com.albert.authorizationserver.controller;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.authorizationserver.dto.JoinRequest;
import com.albert.authorizationserver.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {

    private static final String RIGHT_EMAIL = "jack@email.com";
    private static final String WRONG_EMAIL = "erroremail";
    private static final String RIGHT_8_PASSWORD = "kkkkk!1S";
    private static final String RIGHT_20_PASSWORD = "loooooooooooooong!1S";
    private static final String WRONG_SHORT_7_PASSWORD = "kkkk!1S";
    private static final String WRONG_LONG_21_PASSWORD = "looooooooooooooong!1S";
    private static final String WRONG_NOT_CONTAIN_UPPERCASE_PASSWORD = "kkkkk!1a";
    private static final String WRONG_NOT_CONTAIN_LOWERCASE_PASSWORD = "KKKKK!1A";
    private static final String WRONG_NOT_CONTAIN_SPECIAL_SYMBOLS_PASSWORD = "KKKKKa1A";
    private static final String RIGHT_3_NICKNAME = "kim";
    private static final String RIGHT_10_NICKNAME = "lonickname";
    private static final String WRONG_SHORT_2_NICKNAME = "kk";
    private static final String WRONG_LONG_11_NICKNAME = "loonickname";
    private static final String OTHER_EMAIL = "other@email.com";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthorizationServerSettings authorizationServerSettings;


    @DisplayName("회원 가입")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class SignInTest {

        @DisplayName("유저를 추가 성공하면 issuer 링크를 보내고 성공 메시지도 보냅니다")
        @Test
        void addUsers() throws Exception {
            // given
            JoinRequest joinRequest = JoinRequest.builder()
                    .email(RIGHT_EMAIL)
                    .nickname(RIGHT_10_NICKNAME)
                    .password(RIGHT_20_PASSWORD)
                    .confirmPassword(RIGHT_20_PASSWORD)
                    .build();
            String issuerUri =
                    authorizationServerSettings.getIssuer() + "/.well-known/openid-configuration";

            // when , then
            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(joinRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                            (MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8")))
                    .andExpect(header().string(HttpHeaders.LOCATION, issuerUri))
                    .andExpect(content().string("성공적으로 유저를 추가했습니다."));
        }

        @DisplayName("가입 요청시 이메일,닉네임,비밀번호 및 확인비빌번호를 잘못 입력하면 다시 가입 페이지로 redirect 한다")
        @ParameterizedTest
        @MethodSource("provideStringsForIsBlank")
        void addUserFailedByAlreadyExistsEmail(String email, String nickname, String password,
                String confirmPassword) throws Exception {
            JoinRequest joinRequest = JoinRequest.builder()
                    .email(email)
                    .nickname(nickname)
                    .password(password)
                    .confirmPassword(confirmPassword)
                    .build();

            // given,when
            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(joinRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        private Stream<Arguments> provideStringsForIsBlank() {
            return Stream.of(
                    Arguments.of(
                            WRONG_EMAIL, WRONG_SHORT_2_NICKNAME, WRONG_SHORT_7_PASSWORD,
                            WRONG_LONG_21_PASSWORD),
                    Arguments.of(
                            WRONG_EMAIL, WRONG_LONG_11_NICKNAME,
                            WRONG_NOT_CONTAIN_LOWERCASE_PASSWORD,
                            WRONG_NOT_CONTAIN_UPPERCASE_PASSWORD),
                    Arguments.of(
                            RIGHT_EMAIL, RIGHT_3_NICKNAME,
                            WRONG_NOT_CONTAIN_SPECIAL_SYMBOLS_PASSWORD, RIGHT_8_PASSWORD),
                    Arguments.of(RIGHT_EMAIL, RIGHT_10_NICKNAME, RIGHT_8_PASSWORD,
                            RIGHT_20_PASSWORD)
            );
        }
    }

    @DisplayName("로그인")
    @Nested
    class LoginTest {

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
                    .andDo(print())
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
                    .andDo(print())
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }

        @DisplayName("로그인 시 이메일이 존재하지 않으면 '/login?error' 페이지로 redirect 한다")
        @Test
        void loginByEmailFailedByEmail() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", OTHER_EMAIL)
                            .param("password", RIGHT_8_PASSWORD))
                    .andDo(print())
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }

        @DisplayName("로그인 시 이메일 형식 맞지 않으면 DB에 접근하지 않고 '/login?error' 페이지로 redirect 한다")
        @Test
        void loginByEmailFailedByEmailType() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", WRONG_EMAIL)
                            .param("password", RIGHT_8_PASSWORD))
                    .andDo(print())
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }

        @DisplayName("로그인 시 비번 형식 맞지 않으면 DB에 접근하지 않고 '/login?error' 페이지로 redirect 한다")
        @Test
        void loginByEmailFailedByPasswordType() throws Exception {
            mockMvc.perform(post("/login")
                            .param("username", RIGHT_EMAIL)
                            .param("password", WRONG_LONG_11_NICKNAME))
                    .andDo(print())
                    .andExpect(status().isFound())
                    .andExpect(redirectedUrl("/login?error"));
        }
    }
}
