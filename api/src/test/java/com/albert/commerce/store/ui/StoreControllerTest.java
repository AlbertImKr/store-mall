package com.albert.commerce.store.ui;

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
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
                .andExpect(redirectedUrl("http://localhost/stores/my"));
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
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.other-store").exists());
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
        mockMvc.perform(get("/stores/my"))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("storeName").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.other-store").exists());
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
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.other-store").exists());
    }

    @DisplayName("스토어 아이디로 스토어 가져온다")
    @Test
    void getStoreSuccess() throws Exception {
        // given
        StoreRequest storeRequest = new StoreRequest(TEST_STORE_NAME);
        UserProfileResponse userProfileResponse = userService.findByEmail("test@email.com");
        storeRequest.setUserId(userProfileResponse.getId());
        StoreResponse storeResponse = storeService.addStore(storeRequest);

        mockMvc.perform(get("/stores/" + storeResponse.getStoreId().getValue()))
                .andDo(print())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.other-store").exists());
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
                .andExpect(jsonPath("_links.other-store").exists());
    }
}
