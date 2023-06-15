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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.albert.commerce.store.application.NewStoreRequest;
import com.albert.commerce.store.application.SellerStoreResponse;
import com.albert.commerce.store.application.SellerStoreService;
import com.albert.commerce.user.application.UserService;
import com.albert.commerce.user.query.UserInfoResponse;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@WithMockUser("test@email.com")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ConsumerStoreControllerTest {

    public static final String TEST_STORE_NAME = "testStore";


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    SellerStoreService sellerStoreService;

    @Autowired
    EntityManager entityManager;


    @BeforeEach
    void saveTestUser() {
        userService.init("test@email.com");
    }

    @DisplayName("스토어 아이디로 스토어 가져온다")
    @Test
    void getStoreSuccess() throws Exception {
        // given
        NewStoreRequest newStoreRequest = new NewStoreRequest(TEST_STORE_NAME);
        UserInfoResponse userInfoResponse = userService.findByEmail("test@email.com");
        newStoreRequest.setUserId(userInfoResponse.id());
        SellerStoreResponse sellerStoreResponse = sellerStoreService.createStore(newStoreRequest);
        entityManager.flush();

        mockMvc.perform(get("/stores/" + sellerStoreResponse.getStoreId().getValue()))
                .andDo(print())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("storeName").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.get-store").exists())
                //restDocs
                .andDo(document(
                                "getStoreSuccess", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
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
                .andExpect(jsonPath("_links.get-store").exists())
                //restDocs
                .andDo(document(
                                "getStoreFailed", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
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
