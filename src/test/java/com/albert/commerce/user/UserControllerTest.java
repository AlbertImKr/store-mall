package com.albert.commerce.user;

import com.albert.commerce.infra.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
public class UserControllerTest {

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
                        .param("email", "jack@email.com")
                        .param("nickname", "jack")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
