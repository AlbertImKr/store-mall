package com.albert.commerce.main;

import com.albert.commerce.infra.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("누구나 메인 페이지를 접근 가능하다")
    @Test
    void viewMain() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
