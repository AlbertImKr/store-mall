package com.albert.commerce.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
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
