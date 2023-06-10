package com.albert.commerce.product.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.commerce.product.application.ProductRequest;
import com.albert.commerce.store.command.application.StoreRequest;
import com.albert.commerce.store.command.application.StoreService;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.query.UserDataDao;
import com.albert.commerce.user.query.UserProfileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@Sql(scripts = "classpath:db/mysql/testUser.sql")
@WithMockUser(username = "test@email.com")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class ProductControllerTest {

    public static final String TEST_PRODUCT_NAME = "testProductName";
    public static final String TEST_DESCRIPTION = "testDescription";
    public static final String TEST_BRAND = "testBrand";
    public static final String TEST_CATEGORY = "testCategory";
    public static final int TEST_PRICE = 10000;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserDataDao userDataDao;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreDao storeDao;

    @DisplayName("My Store에서 Product를 추가한다")
    @Test
    void addProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest(
                TEST_PRODUCT_NAME, TEST_PRICE, TEST_DESCRIPTION, TEST_BRAND, TEST_CATEGORY
        );
        UserProfileResponse userProfileResponse = userDataDao.findByEmail("test@email.com")
                .orElseThrow();
        StoreRequest storeRequest = new StoreRequest("store");
        storeRequest.setUserId(userProfileResponse.getId());
        storeService.addStore(storeRequest);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                        .accept(MediaTypes.HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("productId").exists())
                .andExpect(jsonPath("productName").value(TEST_PRODUCT_NAME))
                .andExpect(jsonPath("price").value(TEST_PRICE))
                .andExpect(jsonPath("description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("brand").value(TEST_BRAND))
                .andExpect(jsonPath("category").value(TEST_CATEGORY))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                .andExpect(jsonPath("_links.add-store").exists())
                .andExpect(jsonPath("_links.other-store").exists())
                //restDocs
                .andDo(document(
                                "addProduct", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("현재 product 링크"),
                                        linkWithRel("my-store").description("My 스토어에 연결한다"),
                                        linkWithRel("add-store").description("My 스토어를 만든다"),
                                        linkWithRel("other-store").optional().description("다른 스토어를 연결한다")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("productId").description("상품 아이디"),
                                        fieldWithPath("productName").description("상품 네이밍"),
                                        fieldWithPath("price").description("상품 가격"),
                                        fieldWithPath("description").description("상품 설명"),
                                        fieldWithPath("brand").description("상품 브랜드"),
                                        fieldWithPath("category").description("상품 카타고리")
                                )
                        )
                );

        Store store = storeDao.findByStoreUserId(storeRequest.getStoreUserId()).orElseThrow();
        assertThat(store.getProductIds()).isNotEmpty();
    }
}
