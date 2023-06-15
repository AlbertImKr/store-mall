package com.albert.commerce.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.albert.commerce.user.application.UserService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WithMockUser(username = "test@email.com")
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {


    @Autowired
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void initUser() {
        userService.init("test@email.com");
    }

    @DisplayName("User info를 가져온다")
    @Test
    void getUserInfo() throws Exception {
        mockMvc.perform(get("/users/profile"))
                .andDo(print())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("nickname").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("role").exists())
                .andExpect(jsonPath("dateOfBirth").exists())
                .andExpect(jsonPath("phoneNumber").exists())
                .andExpect(jsonPath("address").exists())
                .andExpect(jsonPath("isActive").exists())
                // restdocs
                .andDo(document("getUserInfo", preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("id").description("아이디"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("role").description("권한"),
                                        fieldWithPath("dateOfBirth").description("생년월"),
                                        fieldWithPath("phoneNumber").description("폰넘버"),
                                        fieldWithPath("address").description("주소"),
                                        fieldWithPath("isActive").description("활성화여부")
                                )
                        )
                );
    }

    @DisplayName("User info를 update한다")
    @Test
    void updateUserInfo() throws Exception {
        mockMvc.perform(put("/users/profile")
                        .queryParam("nickname", "updateNickname")
                        .queryParam("dateOfBirth", String.valueOf(LocalDate.now()))
                        .queryParam("phoneNumber", "01011112222")
                        .queryParam("address", "인천시남동구")
                )
                .andDo(print())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("nickname").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("role").exists())
                .andExpect(jsonPath("dateOfBirth").exists())
                .andExpect(jsonPath("phoneNumber").exists())
                .andExpect(jsonPath("address").exists())
                .andExpect(jsonPath("isActive").exists())
                // restdocs
                .andDo(document("updateUserInfo",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("id").description("아이디"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("role").description("권한"),
                                        fieldWithPath("dateOfBirth").description("생년월"),
                                        fieldWithPath("phoneNumber").description("폰넘버"),
                                        fieldWithPath("address").description("주소"),
                                        fieldWithPath("isActive").description("활성화여부")
                                )
                        )
                );
    }

}
