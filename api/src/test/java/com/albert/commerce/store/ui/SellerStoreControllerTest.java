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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.store.infra.presentation.imports.StoreJpaRepository;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.infra.persistance.imports.UserJpaRepository;
import com.albert.commerce.user.query.domain.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WithMockUser("test@email.com")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SellerStoreControllerTest {

    private static final String TEST_STORE_NAME = "testStoreName";
    private static final String TEST_EMAIL = "test@email.com";
    private static final String TEST_OWNER = "testOwner";
    private static final String TEST_PHONE_NUMBER = "01011001100";
    private static final String TEST_ADDRESS = "testAddress";
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

    @Autowired
    UserDao userDao;

    @Autowired
    StoreJpaRepository storeJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @BeforeEach
    void saveTestUser() {
        userService.createByEmail(TEST_EMAIL);
    }

    @AfterEach
    void clear() {
        storeJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }


    @DisplayName("정상적으로 스토어를 생성한다")
    @Test
    void createStoreSuccess() throws Exception {
        NewStoreRequest newStoreRequest = NewStoreRequest.builder()
                .storeName(TEST_STORE_NAME)
                .email(TEST_EMAIL)
                .ownerName(TEST_OWNER)
                .phoneNumber(TEST_PHONE_NUMBER)
                .address(TEST_ADDRESS)
                .build();
        mockMvc.perform(post("/stores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStoreRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("address").exists())
                .andExpect(jsonPath("phoneNumber").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("ownerName").exists())
                .andExpect(redirectedUrl(BusinessLinks.MY_STORE.toUri().toString()))
                //restDocs
                .andDo(document(
                                "createStoreSuccess", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("지금 요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("storeId").description("스토어 아이디"),
                                        fieldWithPath("storeName").description("스토어 네이밍"),
                                        fieldWithPath("address").description("스토어 주소"),
                                        fieldWithPath("phoneNumber").description("스토어 연락처"),
                                        fieldWithPath("email").description("스토어 이메일"),
                                        fieldWithPath("ownerName").description("스토어 소유주")
                                )
                        )
                );
    }

    @DisplayName("스토어가 이미 추가 되여 있으면 에러 메시지를 응답한다")
    @Test
    void createStoreFailed() throws Exception {
        // given
        NewStoreRequest newStoreRequest = NewStoreRequest.builder()
                .storeName(TEST_STORE_NAME)
                .email(TEST_EMAIL)
                .ownerName(TEST_OWNER)
                .phoneNumber(TEST_PHONE_NUMBER)
                .address(TEST_ADDRESS)
                .build();
        sellerStoreService.createStore(newStoreRequest, TEST_EMAIL);

        // when
        mockMvc.perform(post("/stores")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(newStoreRequest)))
                .andDo(print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error-message").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                //restDocs
                .andDo(document(
                                "createStoreFailed", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다")
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
        NewStoreRequest newStoreRequest = NewStoreRequest.builder()
                .storeName(TEST_STORE_NAME)
                .email(TEST_EMAIL)
                .ownerName(TEST_OWNER)
                .phoneNumber(TEST_PHONE_NUMBER)
                .address(TEST_ADDRESS)
                .build();
        sellerStoreService.createStore(newStoreRequest, TEST_EMAIL);

        // when
        mockMvc.perform(get("/stores/my")
                        .contentType(MediaTypes.HAL_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("storeName").exists())
                .andExpect(jsonPath("address").exists())
                .andExpect(jsonPath("phoneNumber").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("ownerName").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                //restDocs
                .andDo(document(
                                "getMyStoreSuccess", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("storeId").description("스토어의 아이디"),
                                        fieldWithPath("storeName").description("스토어 네이밍"),
                                        fieldWithPath("address").description("스토어 주소"),
                                        fieldWithPath("phoneNumber").description("스토어 연락처"),
                                        fieldWithPath("email").description("스토어 이메일"),
                                        fieldWithPath("ownerName").description("스토어 소유주")
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
                .andExpect(jsonPath("_links.create-store").exists())
                //restDocs
                .andDo(document(
                                "getMyStoreFailed", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("create-store").description("My 스토어를 만든다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("error-message").description("예외 메시지")
                                )
                        )
                );
    }


    @DisplayName("스토어 정보를 update 한다")
    @Test
    void updateMyStore() throws Exception {
        NewStoreRequest newStoreRequest = NewStoreRequest.builder()
                .storeName(TEST_STORE_NAME)
                .email(TEST_EMAIL)
                .ownerName(TEST_OWNER)
                .phoneNumber(TEST_PHONE_NUMBER)
                .address(TEST_ADDRESS)
                .build();
        sellerStoreService.createStore(newStoreRequest, TEST_EMAIL);

        String newStoreName = "newStoreName";
        String newEmail = "new@email.com";
        String newOwnerName = "newOwnerName";
        String newPhoneNumber = "1111111111";
        String newAddress = "newAddress";
        UpdateStoreRequest updateStoreRequest = UpdateStoreRequest.builder()
                .storeName(newStoreName)
                .email(newEmail)
                .ownerName(newOwnerName)
                .phoneNumber(newPhoneNumber)
                .address(newAddress)
                .build();
        // when
        mockMvc.perform(put("/stores/my")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updateStoreRequest))
                )
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("storeName").value(newStoreName))
                .andExpect(jsonPath("address").value(newAddress))
                .andExpect(jsonPath("phoneNumber").value(newPhoneNumber))
                .andExpect(jsonPath("email").value(newEmail))
                .andExpect(jsonPath("ownerName").value(newOwnerName))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                //restDocs
                .andDo(document(
                                "updateMyStore", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("요청한 링크"),
                                        linkWithRel("my-store").description("My 스토어를 찾는다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("storeId").description("스토어의 아이디"),
                                        fieldWithPath("storeName").description("스토어 네이밍"),
                                        fieldWithPath("address").description("스토어 주소"),
                                        fieldWithPath("phoneNumber").description("스토어 연락처"),
                                        fieldWithPath("email").description("스토어 이메일"),
                                        fieldWithPath("ownerName").description("스토어 소유주")
                                )
                        )
                );

    }


}
