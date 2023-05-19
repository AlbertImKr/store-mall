package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    public static final String TEST_EMAIL = "jack@email.com";
    public static final String TEST_NICKNAME = "jack";
    public static final String TEST_PASSWORD = "testPassword";

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
    void addUser() throws Exception {
        mockMvc.perform(post("/users")
                        .param("email", TEST_EMAIL)
                        .param("nickname", TEST_NICKNAME)
                        .param("password", TEST_PASSWORD)
                        .param("confirmPassword", TEST_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    @DisplayName("로그인 한 사용자는 자신의 profile를 볼수 있다")
    @WithTestUser(username = TEST_NICKNAME)
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


    @DisplayName("이메일로 로그인 성공하면 메인페이지로 리다이렉트 한다")
    @Test
    void loginByEmail() throws Exception {
        // given
        JoinRequest joinRequest = new JoinRequest(
                TEST_EMAIL,
                TEST_NICKNAME,
                TEST_PASSWORD,
                TEST_PASSWORD);
        userService.save(joinRequest);

        // when
        mockMvc.perform(post("/login")
                        .param("username", TEST_EMAIL)
                        .param("password", TEST_PASSWORD))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());
    }

}
