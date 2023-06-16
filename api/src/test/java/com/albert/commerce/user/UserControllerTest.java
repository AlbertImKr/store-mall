package com.albert.commerce.user;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
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
                .andExpect(jsonPath("active").exists())
                // restdocs
                .andDo(document("getUserInfo", preprocessResponse(prettyPrint()),
                        links(
                                halLinks(),
                                linkWithRel("self").description("요청한 링크"),
                                linkWithRel("create-store").description("My 스토어를 만든다"),
                                linkWithRel("get-store").description("스토어를 연결한다"),
                                linkWithRel("my-store").description("My 스토어 연결한다")
                        ),
                        responseFields(
                                subsectionWithPath("_links").ignored(),
                                fieldWithPath("id").description("아이디"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("role").description("권한"),
                                fieldWithPath("dateOfBirth").description("생년월"),
                                fieldWithPath("phoneNumber").description("폰넘버"),
                                fieldWithPath("address").description("주소"),
                                fieldWithPath("active").description("활성화여부")
                        )
                        )
                );
    }

    @DisplayName("User info를 update한다")
    @Test
    void updateUserInfo() throws Exception {
        String updateNickname = "updateNickname";
        String dateOfBirth = String.valueOf(LocalDate.now());
        String phoneNumber = "01011112222";
        String address = "인천시남동구";
        mockMvc.perform(put("/users/profile")
                        .queryParam("nickname", updateNickname)
                        .queryParam("dateOfBirth", dateOfBirth)
                        .queryParam("phoneNumber", phoneNumber)
                        .queryParam("address", address)
                )
                .andDo(print())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("nickname").value(updateNickname))
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("role").exists())
                .andExpect(jsonPath("dateOfBirth").value(dateOfBirth))
                .andExpect(jsonPath("phoneNumber").value(phoneNumber))
                .andExpect(jsonPath("address").value(address))
                .andExpect(jsonPath("active").exists())
                // restdocs
                .andDo(document("updateUserInfo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        links(
                                halLinks(),
                                linkWithRel("self").description("요청한 링크"),
                                linkWithRel("create-store").description("My 스토어를 만든다"),
                                linkWithRel("get-store").description("스토어를 연결한다"),
                                linkWithRel("my-store").description("My 스토어 연결한다")
                        ),
                        responseFields(
                                subsectionWithPath("_links").ignored(),
                                fieldWithPath("id").description("아이디"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("role").description("권한"),
                                fieldWithPath("dateOfBirth").description("생년월"),
                                fieldWithPath("phoneNumber").description("폰넘버"),
                                fieldWithPath("address").description("주소"),
                                fieldWithPath("active").description("활성화여부")
                        )
                        )
                );
    }

}
