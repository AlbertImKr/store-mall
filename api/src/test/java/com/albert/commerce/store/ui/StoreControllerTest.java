package com.albert.commerce.store.ui;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.commerce.store.command.application.StoreRequest;
import com.albert.commerce.store.command.application.StoreResponse;
import com.albert.commerce.store.command.application.StoreService;
import com.albert.commerce.user.application.UserService;
import com.albert.commerce.user.query.UserProfileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@WithMockUser("test@email.com")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class StoreControllerTest {

    public static final String TEST_STORE_NAME = "testStore";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    StoreService storeService;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void saveTestUser() {
        userService.init("test@email.com");
    }

    @DisplayName("정상적으로 스토어를 추가한다")
    @Test
    void addStoreSuccess() throws Exception {
        mockMvc.perform(post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_STORE_NAME)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.get-store").exists())
                .andExpect(redirectedUrl("http://localhost:8080/stores/my"))
                //restDocs
                .andDo(document(
                                "addStoreSuccess", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("지금 요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다"),
                                        linkWithRel("add-store").description("My 스토어를 만든다"),
                                        linkWithRel("get-store").optional()
                                                .description("다른 스토어를 연결한다")
                                )
                        )
                );
    }

    @DisplayName("스토어가 이미 추가 되여 있으면 에러 메시지를 응답한다")
    @Test
    void addStoreFailed() throws Exception {
        // given
        StoreRequest storeRequest = new StoreRequest(TEST_STORE_NAME);
        UserProfileResponse userProfileResponse = userService.findByEmail("test@email.com");
        storeRequest.setUserId(userProfileResponse.getId());
        storeService.addStore(storeRequest);

        // when
        mockMvc.perform(post("/stores")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(TEST_STORE_NAME)))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error-message").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.get-store").exists())
                //restDocs
                .andDo(document(
                                "addStoreFailed", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다"),
                                        linkWithRel("add-store").description("My 스토어를 만든다"),
                                        linkWithRel("get-store").optional()
                                                .description("다른 스토어를 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("error-message").description("예외 메시지")
                                )
                        )
                );
    }

    @DisplayName("내 스토어 정보를 가져온다")
    @Test
    void getMyStoreSuccess() throws Exception {
        // given
        StoreRequest storeRequest = new StoreRequest(TEST_STORE_NAME);
        UserProfileResponse userProfileResponse = userService.findByEmail("test@email.com");
        storeRequest.setUserId(userProfileResponse.getId());
        storeService.addStore(storeRequest);

        // when
        mockMvc.perform(get("/stores/my")
                        .contentType(MediaTypes.HAL_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("storeName").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.get-store").exists())
                //restDocs
                .andDo(document(
                                "getMyStoreSuccess", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어 연결한다"),
                                        linkWithRel("add-store").description("My 스토어를 만든다"),
                                        linkWithRel("get-store").optional()
                                                .description("다른 스토어를 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("storeId").description("스토어의 아이디"),
                                        fieldWithPath("storeName").description("스토어의 이름")
                                )
                        )
                );
    }

    @DisplayName("스토어 정보를 가져올 스토어가 존재하지 않으면 에러 메시지를 응답한다")
    @Test
    void getMyStoreFailed() throws Exception {

        // when
        mockMvc.perform(get("/stores/my"))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error-message").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.get-store").exists())
                //restDocs
                .andDo(document(
                                "getMyStoreFailed", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다"),
                                        linkWithRel("add-store").description("My 스토어를 만든다"),
                                        linkWithRel("get-store").optional()
                                                .description("다른 스토어를 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("error-message").description("예외 메시지")
                                )
                        )
                );
    }

    @DisplayName("스토어 아이디로 스토어 가져온다")
    @Test
    void getStoreSuccess() throws Exception {
        // given
        StoreRequest storeRequest = new StoreRequest(TEST_STORE_NAME);
        UserProfileResponse userProfileResponse = userService.findByEmail("test@email.com");
        storeRequest.setUserId(userProfileResponse.getId());
        StoreResponse storeResponse = storeService.addStore(storeRequest);
        entityManager.flush();

        mockMvc.perform(get("/stores/" + storeResponse.getStoreId().getValue()))
                .andDo(print())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("storeName").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.get-store").exists())
                //restDocs
                .andDo(document(
                                "getStoreSuccess", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다"),
                                        linkWithRel("add-store").description("My 스토어를 만든다"),
                                        linkWithRel("get-store").optional()
                                                .description("다른 스토어를 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("storeId").description("스토어 아이디"),
                                        fieldWithPath("storeName").description("스토어 이름")
                                )
                        )
                );
    }

    @DisplayName("스토어 아이디로 존재하지 않으면 에러 메시지를 응답한다")
    @Test
    void getStoreFailed() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(get("/stores/" + uuid))
                .andDo(print())
                .andExpect(jsonPath("error-message").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.get-store").exists())
                //restDocs
                .andDo(document(
                                "getStoreFailed", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다"),
                                        linkWithRel("add-store").description("My 스토어를 만든다"),
                                        linkWithRel("get-store").optional()
                                                .description("다른 스토어를 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("error-message").description("예외 메시지")
                                )
                        )
                );
    }
}
